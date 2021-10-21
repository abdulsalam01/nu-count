package com.nu.nucount.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.nu.nucount.R
import com.nu.nucount.core.constant.API
import com.nu.nucount.core.helper.GlobalHelper
import com.nu.nucount.core.session.Session
import com.nu.nucount.ui.fragment.InputFragment
import com.nu.nucount.ui.fragment.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nu.nucount.core.constant.Service
import com.nu.nucount.core.helper.ConnectionChecker
import com.nu.nucount.extension.database.*
import com.nu.nucount.extension.singleton.ServiceManager
import com.nu.nucount.model.*
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var service: Service
    private lateinit var progressBar: ProgressBar
    private lateinit var fmLayout: FrameLayout

    private var exit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setNotificationOpenedHandler(FirebaseNotificationOpenedHandler(this))
        OneSignal.setAppId(API.appID)

        this.toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(this.toolbar)

        this.service = ServiceManager.getInstance()
        this.progressBar = this.findViewById(R.id.progress)
        this.bottomNav = this.findViewById(R.id.bottom_menu)
        this.fmLayout = this.findViewById(R.id.container)
        this.bottomNav.setOnNavigationItemSelectedListener { it -> onNavigationItemSelected(it) }

        this.progressBar.rootView.bringToFront()

        GlobalHelper.loadFragment(this, InputFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment: Fragment

        when (item.itemId) {
            R.id.input_menu -> fragment = InputFragment()
//            R.id.notification_menu -> fragment = NotificationFragment()
            R.id.exit_menu -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.text_logout))
                    .setNegativeButton(resources.getString(R.string.tidak)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.ya)) { dialog, which ->
                        Session.clearUser(this)
                        dialog.dismiss()
                    }
                    .show()
                return true
            }
        }

        return GlobalHelper.loadFragment(this, fragment)
    }

    private class FirebaseNotificationOpenedHandler(val context: Context?): OneSignal.OSNotificationOpenedHandler {

        override fun notificationOpened(result: OSNotificationOpenedResult?) {
            val actionType = result!!.action.type
            val data: JSONObject = result.notification.additionalData
            val bundle = Bundle()
            val notif = NotificationFragment()

            bundle.putString("data", data.getString("data"))
            notif.arguments = bundle

            GlobalHelper.loadFragment(context!!, notif)
        }
    }

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_LONG).show()
            exit = true
            Handler().postDelayed({ exit = false }, 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.syncronize_menu -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Lakukan proses sinkronisasi?")
                    .setNegativeButton(resources.getString(R.string.tidak)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.ya)) { dialog, which ->
                        syncronizeAll()
                        dialog.dismiss()
                    }
                    .show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun syncronizeAll() {
        if (ConnectionChecker.isNetworkAvailable(this)) {
            insertToDesaChaining()
        } else {
            Toast.makeText(this, "Tidak terhubung dengan internet!",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertToDesaChaining() {
        this.fmLayout.isEnabled = false
        this.progressBar.visibility = View.VISIBLE

        this.service.getDesa().enqueue(object : Callback<Desa.Response> {
            override fun onFailure(call: Call<Desa.Response>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Desa.Response>, response: Response<Desa.Response>) {
                val data = response.body()!!.data
                val desaOperation = DesaOperation(this@MainActivity)

                desaOperation.deleteAll()
                for (values in data) {
                    desaOperation.create(Desa(values.id_desa, values.nama_desa))
                }

                // chaining - 1
                insertToJabatan()
            }

        })
    }

    private fun insertToJabatan() {
        this.service.getJabatan().enqueue(object : Callback<Jabatan.Response> {
            override fun onFailure(call: Call<Jabatan.Response>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(),
                    Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Jabatan.Response>,
                response: Response<Jabatan.Response>
            ) {
                val data = response.body()!!.data
                val jabatanOperation = JabatanOperation(this@MainActivity)

                jabatanOperation.deleteAll()
                for (values in data) {
                    jabatanOperation.create(Jabatan(values.id_jabatan, values.nama_jabatan))
                }

                // chaining-2
                insertIntoKecamatan()
            }
        })
    }

    private fun insertIntoKecamatan() {
        this.service.getKecamatan().enqueue(object : Callback<Kecamatan.Response> {

            override fun onFailure(call: Call<Kecamatan.Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Kecamatan.Response>,
                response: Response<Kecamatan.Response>
            ) {
                val data = response.body()!!.data
                val kecamatanOperation = KecamatanOperation(this@MainActivity)

                kecamatanOperation.deleteAll()
                for (values in data) {
                    kecamatanOperation.create(Kecamatan(values.id_kecamatan, values.nama_kecamatan))
                }

                // chaining - 3
                insertIntoPekerjaan()
            }

        })
    }

    private fun insertIntoPekerjaan() {
        this.service.getPekerjaan().enqueue(object : Callback<Pekerjaan.Response> {
            override fun onFailure(call: Call<Pekerjaan.Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Pekerjaan.Response>,
                response: Response<Pekerjaan.Response>
            ) {
                val data = response.body()!!.data
                val pekerjaanOperation = PekerjaanOperation(this@MainActivity)

                pekerjaanOperation.deleteAll()
                for (values in data) {
                    pekerjaanOperation.create(Pekerjaan(values.id_pekerjaan, values.nama_pekerjaan))
                }

                // chaining -4
                insertIntoRt()
            }
        })
    }

    private fun insertIntoRt() {
        this.service.getRt().enqueue(object : Callback<Rt.Response> {
            override fun onFailure(call: Call<Rt.Response>, t: Throwable) {
            }

            override fun onResponse(call: Call<Rt.Response>, response: Response<Rt.Response>) {
                val data = response.body()!!.data
                val rtOperation = RtOperation(this@MainActivity)

                rtOperation.deleteAll()
                for (values in data) {
                    rtOperation.create(Rt(values.id_rt, values.nama_rt))
                }

                // chaining - 5
                insertIntoRw()
            }
        })
    }

    private fun insertIntoRw() {
        this.service.getRw().enqueue(object : Callback<Rw.Response> {

            override fun onFailure(call: Call<Rw.Response>, t: Throwable) {
            }

            override fun onResponse(call: Call<Rw.Response>, response: Response<Rw.Response>) {
                val data = response.body()!!.data
                val rwOperation = RwOperation(this@MainActivity)

                rwOperation.deleteAll()
                for (values in data) {
                    rwOperation.create(Rw(values.id_rw, values.nama_rw))
                }

                // chaininng-6
                insertIntoStruktur()
            }

        })
    }

    private fun insertIntoStruktur() {
        this.service.getStruktur().enqueue(object : Callback<Struktur.Response> {
            override fun onFailure(call: Call<Struktur.Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Struktur.Response>,
                response: Response<Struktur.Response>
            ) {
                val data = response.body()!!.data
                val strukturOperation = StrukturOperation(this@MainActivity)

                strukturOperation.deleteAll()
                for (values in data) {
                    strukturOperation.create(Struktur(values.id_struktur, values.nama_struktur))
                }

                // chaining - 7
                insertIntoSub1()
            }
        })
    }

    private fun insertIntoSub1() {
        this.service.getSubAllPekerjaan1().enqueue(object: Callback<SubPekerjaan1.Response> {
            override fun onFailure(call: Call<SubPekerjaan1.Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<SubPekerjaan1.Response>,
                response: Response<SubPekerjaan1.Response>
            ) {
                val data = response.body()!!.data
                val subOperation = SubPekerjaan1Operation(this@MainActivity)

                subOperation.deleteAll()
                for (values in data) {
                    subOperation.create(
                        SubPekerjaan1(values.id_sub, values.nama_sub,
                        values.id_tb_pekerjaan)
                    )
                }

                // chaining - last
                insertIntoSub2()
            }

        })
    }

    private fun insertIntoSub2() {
        this.service.getSubAllPekerjaan2().enqueue(object: Callback<SubPekerjaan2.Response> {
            override fun onFailure(call: Call<SubPekerjaan2.Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<SubPekerjaan2.Response>,
                response: Response<SubPekerjaan2.Response>
            ) {
                val data = response.body()!!.data
                val subOperation = SubPekerjaan2Operation(this@MainActivity)

                subOperation.deleteAll()
                for (values in data) {
                    subOperation.create(
                        SubPekerjaan2(values.id_sub2, values.nama_sub2,
                            values.id_tb_sub1)
                    )
                }

                fmLayout.isEnabled = true
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity,
                    "Berhasil rekap ke local",
                    Toast.LENGTH_SHORT).show()

                GlobalHelper.loadFragment(this@MainActivity, InputFragment())
            }

        })
    }
}