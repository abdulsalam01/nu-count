package com.example.nucount.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.nucount.R
import com.example.nucount.core.constant.Service
import com.example.nucount.core.helper.ConnectionChecker
import com.example.nucount.core.session.Session
import com.example.nucount.extension.database.MemberOperation
import com.example.nucount.extension.singleton.ServiceManager
import com.example.nucount.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var memberOperation: MemberOperation

    private lateinit var service: Service

    private lateinit var btnAddDynamicForm: Button
    private lateinit var btnRemoveDynamicForm: Button
    private lateinit var btnSubmit: Button

    private lateinit var spinnerStatus: Spinner
    private lateinit var spinnerJenisKelamin: Spinner
    private lateinit var spinnerKabupaten: Spinner
    private lateinit var spinnerKecamatan: Spinner
    private lateinit var spinnerDesa: Spinner
    private lateinit var spinnerDusun: Spinner
    private lateinit var spinnerRt: Spinner
    private lateinit var spinnerRw: Spinner
    private lateinit var spinnerPendidikan: Spinner
    private lateinit var spinnerPekerjaan: Spinner
    private lateinit var spinnerSubPekerjaan1: Spinner
    private lateinit var spinnerSubPekerjaan2: Spinner
    private lateinit var spinnerPenghasilan: Spinner
    private lateinit var spinnerAnggota: Spinner

    private lateinit var txtNamaLengkap : TextInputEditText
    private lateinit var txtNik: TextInputEditText
    private lateinit var txtTanggalLahir: TextInputEditText // yyyy-mm-dd
    private lateinit var txtTempatLahir: TextInputEditText
    private lateinit var txtNomorHp: TextInputEditText
    private lateinit var txtUmur: TextInputEditText
    private lateinit var txtSubPekerjaan3: TextInputEditText

    private lateinit var formFamily: LinearLayout
    private lateinit var formSub: LinearLayout

    private var isOverThan59: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_input, container, false)

        this.memberOperation = MemberOperation(requireContext())
        this.btnAddDynamicForm = v.findViewById(R.id.btn_add_dynamic_form)
        this.btnRemoveDynamicForm = v.findViewById(R.id.btn_remove_dynamic_form)
        this.btnSubmit = v.findViewById(R.id.btn_submit)
        this.spinnerStatus = v.findViewById(R.id.spinner_status)
        this.spinnerJenisKelamin = v.findViewById(R.id.spinner_jenis_kelamin)
        this.spinnerKabupaten = v.findViewById(R.id.spinner_kabupaten)
        this.spinnerKecamatan = v.findViewById(R.id.spinner_kecamatan)
        this.spinnerDesa = v.findViewById(R.id.spinner_desa)
        this.spinnerDusun = v.findViewById(R.id.spinner_dusun)
        this.spinnerRt = v.findViewById(R.id.spinner_rt)
        this.spinnerRw = v.findViewById(R.id.spinner_rw)
        this.spinnerPendidikan = v.findViewById(R.id.spinner_pendidikan)
        this.spinnerPekerjaan = v.findViewById(R.id.spinner_pekerjaan)
        this.spinnerSubPekerjaan1 = v.findViewById(R.id.spinner_subpekerjaan1)
        this.spinnerSubPekerjaan2 = v.findViewById(R.id.spinner_subpekerjaan2)
        this.spinnerPenghasilan = v.findViewById(R.id.spinner_penghasilan)
        this.spinnerAnggota = v.findViewById(R.id.spinner_anggota)
        this.txtNamaLengkap = v.findViewById(R.id.txt_nama_lengkap)
        this.txtNik = v.findViewById(R.id.txt_nik)
        this.txtNomorHp = v.findViewById(R.id.txt_nomor_hp)
        this.txtTanggalLahir = v.findViewById(R.id.txt_tanggal_lahir)
        this.txtTempatLahir = v.findViewById(R.id.txt_tempat_lahir)
        this.txtUmur = v.findViewById(R.id.txt_umur)
        this.txtSubPekerjaan3 = v.findViewById(R.id.txt_subpekerjaan_3)
        this.formFamily = v.findViewById(R.id.sub_dynamic)
        this.formSub = v.findViewById(R.id.sub_form)

        return v
    }

    fun deleteField(view: View) {
        this.formFamily.removeView(view.parent as View)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.service = ServiceManager.getInstance()

        initLoad()

        this.btnAddDynamicForm.setOnClickListener {
            if (!isOverThan59) {
                val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val rowFamily = inflater.inflate(R.layout.row_family, null)

                this.formFamily.addView(rowFamily, this.formFamily.childCount - 1)
            }
        }

        this.btnRemoveDynamicForm.setOnClickListener {
            if (this.formFamily.childCount - 1 > -1)
                this.formFamily.removeView(this.formFamily.get(this.formFamily.childCount - 1))
        }

        this.txtUmur.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isOverThan59 = !(!s.toString().isEmpty() && s.toString().toInt() < 60)

                if (!isOverThan59)
                    formSub.visibility = View.VISIBLE
                else
                    formSub.visibility = View.GONE
            }

        })

        this.spinnerPekerjaan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pekerjaan = parent!!.getItemAtPosition(position) as Pekerjaan
                loadSubPekerjaan1(pekerjaan.id_pekerjaan.toInt())
            }
        }


        this.spinnerSubPekerjaan1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val subPekerjaan1 = parent!!.getItemAtPosition(position) as SubPekerjaan1
                loadSubPekerjaan2(subPekerjaan1.id_sub.toInt())
            }
        }

        this.btnSubmit.setOnClickListener {
            onSubmit(ConnectionChecker.isNetworkAvailable(requireContext()))
        }
    }

    private fun initLoad() {
        loadStatus()
        loadJenisKelamin()
        loadKabupaten()
        loadKecamatan()
        loadDesa()
        loadDusun()
        loadRt()
        loadRw()
        loadPendidikan()
        loadPekerjaan()
        loadPenghasilan()
        loadAnggota()
    }

    private fun onSubmit(connection: Boolean) {
        val data = HashMap<String, Any>()
        val idSession = Session.getCurrentUser(requireContext()).id_user
        val arrFamily = ArrayList<Family>()

        data["nama_lengkap"] = txtNamaLengkap.text.toString()
        data["nik"] = txtNik.text.toString()
        data["status_pernikahan"] = spinnerStatus.selectedItem
        data["tanggal_lahir"] = txtTanggalLahir.text.toString() //yyyy-mm-dd
        data["tempat_lahir"] = txtTempatLahir.text.toString()
        data["jenis_kelamin"] = spinnerJenisKelamin.selectedItem
        data["nomor"] = txtNomorHp.text.toString()
        data["kecamatan"] = (spinnerKecamatan.selectedItem as Kecamatan).id_kecamatan
        data["desa"] = (spinnerDesa.selectedItem as Desa).id_desa
        data["dusun"] = (spinnerDusun.selectedItem as Dusun).id_dusun
        data["rt"] = (spinnerRt.selectedItem as Rt).id_rt
        data["rw"] = (spinnerRw.selectedItem as Rw).id_rw
        // init null-value - safety insert to local
        data["pendidikan"] = ""
        data["pekerjaan"] = ""
        data["sub_pekerjaan"] = ""
        data["sub_pekerjaan_2"] = ""
        data["sub_pekerjaan_3"] = ""
        data["penghasilan"] = ""
        data["anggota"] = ""

        if(!isOverThan59) {
            // some of rest code
            data["pendidikan"] = spinnerPendidikan.selectedItemPosition + 1
            data["pekerjaan"] = (spinnerPekerjaan.selectedItem as Pekerjaan).id_pekerjaan
            data["sub_pekerjaan"] = (spinnerSubPekerjaan1.selectedItem as SubPekerjaan1).id_sub
            data["sub_pekerjaan_2"] = (spinnerSubPekerjaan2.selectedItem as SubPekerjaan2).id_sub2
            data["sub_pekerjaan_3"] = txtSubPekerjaan3.text.toString()
            data["penghasilan"] = spinnerPenghasilan.selectedItemPosition + 1
            data["anggota"] = spinnerAnggota.selectedItemPosition + 1

            val jsonArrFamily = JSONArray()
            for (i in 0..this.formFamily.childCount - 1) {
                val txtNama = this.formFamily[i].findViewById<TextInputEditText>(R.id.txt_nama)
                val txtUsia = this.formFamily[i].findViewById<TextInputEditText>(R.id.txt_usia)
                val spinnerHk = this.formFamily[i].findViewById<Spinner>(R.id.spinner_hk)
                val spinnerPendidikanOr = this.formFamily[i].findViewById<Spinner>(R.id.spinner_pendidikan_or)

                val jsonObjFamily = JSONObject()
                jsonObjFamily.put("nama_keluarga", txtNama.text)
                jsonObjFamily.put("usia", txtUsia.text)
                jsonObjFamily.put("hk_keluarga", spinnerHk.selectedItem)
                jsonObjFamily.put("pendidikan", spinnerPendidikanOr.selectedItemPosition + 1)
                jsonArrFamily.put(i, jsonObjFamily)

                val family = Family(jsonObjFamily.getString("nama_keluarga"),
                    jsonObjFamily.getString("usia"),
                    jsonObjFamily.getString("hk_keluarga"),
                    jsonObjFamily.getString("pendidikan"),
                    0
                )
                arrFamily.add(family)
            }

            data["keluarga"] = jsonArrFamily.toString()
        }

        // over internet
        if (connection) {
            Toast.makeText(requireContext(),
                "${memberOperation.countByIdSession(idSession)} remaining",
                Toast.LENGTH_SHORT)
                .show()

            this.getFromLocalRoom(memberOperation.countByIdSession(idSession), idSession)
            this.onSubmitOverInternet(idSession, data)
        // offline mode
        } else {
            val member = Member(0, data["nama_lengkap"].toString(), data["nik"].toString(),
                data["status_pernikahan"].toString(), data["tanggal_lahir"].toString(),
                data["tempat_lahir"].toString(), data["jenis_kelamin"].toString(),
                data["nomor"].toString(), "BANYUWANGI",
                data["kecamatan"].toString(), data["desa"].toString(),
                data["dusun"].toString(), data["rt"].toString(), data["rw"].toString(),
                data["pendidikan"].toString(), data["pekerjaan"].toString(),
                data["sub_pekerjaan"].toString(), data["sub_pekerjaan_2"].toString(),
                data["sub_pekerjaan_3"].toString(), data["penghasilan"].toString(),
                data["anggota"].toString(), idSession, arrFamily
            )

            this.onSubmitOverLocalRoom(member)
        }
    }

    private fun onSubmitOverLocalRoom(data: Member) {
        val status = this.memberOperation.create(data)

        if (status > 0) {
            Toast.makeText(
                requireContext(), "Berhasil menambahkan ke local: ${status}",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                requireContext(), "Gagal menambah ke local!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getFromLocalRoom(counter: Int, id: Int) {
        if (counter > 0) {
            val memberOperation = MemberOperation(requireContext())
            val localEntry = memberOperation.getByIdPetugas(id)

            for (i in 0..localEntry.size - 1) {
                val member = localEntry.get(i)
                val data = HashMap<String, Any>()

                data["nama_lengkap"] = member.namaLengkap
                data["nik"] = member.nik
                data["status_pernikahan"] = member.statusNikah
                data["tanggal_lahir"] = member.tanggalLahir.toString()
                data["tempat_lahir"] = member.tempatLahir
                data["jenis_kelamin"] = member.jenisKelamin
                data["nomor"] = member.nomor
                data["kecamatan"] = member.kecamatan
                data["desa"] = member.desa
                data["dusun"] = member.dusun
                data["rt"] = member.rt
                data["rw"] = member.rw
                data["pendidikan"] = member.pendidikan.toString()
                data["pekerjaan"] = member.pekerjaan.toString()
                data["sub_pekerjaan"] = member.subPekerjaan1.toString()
                data["sub_pekerjaan_2"] = member.subPekerjaan2.toString()
                data["sub_pekerjaan_3"] = member.subPekerjaan3.toString()
                data["penghasilan"] = member.penghasilan.toString()
                data["anggota"] = member.anggota

                val jsonArrFamily = JSONArray()
                if (member.family!!.size > 0) {

                    for (j in 0..member.family.size - 1) {
                        val family = member.family.get(j)
                        val jsonObjFamily = JSONObject()

                        jsonObjFamily.put("nama_keluarga", family.nama)
                        jsonObjFamily.put("usia", family.usia)
                        jsonObjFamily.put("hk_keluarga", family.hk)
                        jsonObjFamily.put("pendidikan", family.pendidikan)
                        jsonArrFamily.put(j, jsonObjFamily)
                    }
                }

                data["keluarga"] = jsonArrFamily.toString()
                this.onSubmitOverInternet(id, data)
                // delete - on over internet
                this.memberOperation.delete(member.id)
            }
        }
    }

    private fun onSubmitOverInternet(idSession: Int, data: HashMap<String, Any>) {
        this.service.inputData(idSession, data).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Snackbar.make(btnSubmit, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Snackbar.make(btnSubmit, "Berhasil upload ke cloud!", Snackbar.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun loadStatus() {
        val data = resources.getStringArray(R.array.status_nikah)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = arrayAdapter
    }

    private fun loadJenisKelamin() {
        val data = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJenisKelamin.adapter = arrayAdapter
    }

    private fun loadKabupaten() {
        val data = resources.getStringArray(R.array.kabupaten)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKabupaten.adapter = arrayAdapter
    }

    private fun loadKecamatan() {
        this.service.getKecamatan().enqueue(object : Callback<Kecamatan.Response> {

            override fun onFailure(call: Call<Kecamatan.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Kecamatan.Response>,
                response: Response<Kecamatan.Response>
            ) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerKecamatan.adapter = arrayAdapter
            }

        })
    }

    private fun loadDesa() {
        this.service.getDesa().enqueue(object : Callback<Desa.Response> {
            override fun onFailure(call: Call<Desa.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Desa.Response>, response: Response<Desa.Response>) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDesa.adapter = arrayAdapter
            }

        })
    }

    private fun loadDusun() {
        this.service.getDusun().enqueue(object : Callback<Dusun.Response> {
            override fun onFailure(call: Call<Dusun.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Dusun.Response>,
                response: Response<Dusun.Response>
            ) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDusun.adapter = arrayAdapter
            }

        })
    }

    private fun loadRt() {
        this.service.getRt().enqueue(object : Callback<Rt.Response> {
            override fun onFailure(call: Call<Rt.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Rt.Response>, response: Response<Rt.Response>) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerRt.adapter = arrayAdapter
            }
        })
    }

    private fun loadRw() {
        this.service.getRw().enqueue(object : Callback<Rw.Response> {

            override fun onFailure(call: Call<Rw.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Rw.Response>, response: Response<Rw.Response>) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerRw.adapter = arrayAdapter
            }

        })
    }

    private fun loadPekerjaan() {
        this.service.getPekerjaan().enqueue(object : Callback<Pekerjaan.Response> {
            override fun onFailure(call: Call<Pekerjaan.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Pekerjaan.Response>,
                response: Response<Pekerjaan.Response>
            ) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPekerjaan.adapter = arrayAdapter
            }
        })
    }

    private fun loadPendidikan() {
        val data = resources.getStringArray(R.array.pendidikan)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPendidikan.adapter = arrayAdapter
    }

    private fun loadPenghasilan() {
        val data = resources.getStringArray(R.array.penghasilan)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPenghasilan.adapter = arrayAdapter
    }

    private fun loadAnggota() {
        val data = resources.getStringArray(R.array.anggota)
        val arrayAdapter = data.let {
            ArrayAdapter(requireContext(),
                    android.R.layout.simple_spinner_item, it
            )
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAnggota.adapter = arrayAdapter
    }

    private fun loadSubPekerjaan1(id: Int) {
        this.service.getSubPekerjaan1(id).enqueue(object : Callback<SubPekerjaan1.Response> {
            override fun onFailure(call: Call<SubPekerjaan1.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<SubPekerjaan1.Response>,
                response: Response<SubPekerjaan1.Response>
            ) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSubPekerjaan1.adapter = arrayAdapter
            }

        })
    }

    private fun loadSubPekerjaan2(id: Int) {
        this.service.getSubPekerjaan2(id).enqueue(object : Callback<SubPekerjaan2.Response> {
            override fun onFailure(call: Call<SubPekerjaan2.Response>, t: Throwable) {
                Snackbar.make(btnAddDynamicForm, t.message.toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<SubPekerjaan2.Response>,
                response: Response<SubPekerjaan2.Response>
            ) {
                val data = response.body()?.data
                val arrayAdapter = data?.let {
                    ArrayAdapter(context!!,
                        android.R.layout.simple_spinner_item, it
                    )
                }

                arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSubPekerjaan2.adapter = arrayAdapter
            }
        })
    }
}