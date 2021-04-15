package com.nu.pcnucount.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nu.pcnucount.R
import com.nu.pcnucount.core.constant.API
import com.nu.pcnucount.core.helper.GlobalHelper
import com.nu.pcnucount.core.session.Session
import com.nu.pcnucount.extension.database.MemberOperation
import com.nu.pcnucount.ui.fragment.InputFragment
import com.nu.pcnucount.ui.fragment.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import org.json.JSONObject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNav: BottomNavigationView

    private var exit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setNotificationOpenedHandler(FirebaseNotificationOpenedHandler(this))
        OneSignal.setAppId(API.appID)

        this.bottomNav = this.findViewById(R.id.bottom_menu)
        this.bottomNav.setOnNavigationItemSelectedListener { it -> onNavigationItemSelected(it) }

        GlobalHelper.loadFragment(this, InputFragment())

        val d = MemberOperation(this)
        d.getAll()
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
//                Session.clearUser(this)
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

}