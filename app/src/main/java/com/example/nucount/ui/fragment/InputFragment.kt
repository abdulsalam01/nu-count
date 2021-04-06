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
import com.example.nucount.extension.singleton.ServiceManager
import com.example.nucount.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    private lateinit var service: Service

    private lateinit var btnAddDynamicForm: Button
    private lateinit var btnRemoveDynamicForm: Button

    private lateinit var spinnerStatus: Spinner
    private lateinit var spinnerJenisKelamin: Spinner
    private lateinit var spinnerKabupaten: Spinner
    private lateinit var spinnerKecamatan: Spinner
    private lateinit var spinnerDesa: Spinner
    private lateinit var spinnerDusun: Spinner
    private lateinit var spinnerRt: Spinner
    private lateinit var spinnerRw: Spinner
    private lateinit var spinnerPekerjaan: Spinner
    private lateinit var spinnerSubPekerjaan1: Spinner
    private lateinit var spinnerSubPekerjaan2: Spinner

    private lateinit var txtUmur: TextInputEditText

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

        this.btnAddDynamicForm = v.findViewById(R.id.btn_add_dynamic_form)
        this.btnRemoveDynamicForm = v.findViewById(R.id.btn_remove_dynamic_form)
        this.spinnerStatus = v.findViewById(R.id.spinner_status)
        this.spinnerJenisKelamin = v.findViewById(R.id.spinner_jenis_kelamin)
        this.spinnerKabupaten = v.findViewById(R.id.spinner_kabupaten)
        this.spinnerKecamatan = v.findViewById(R.id.spinner_kecamatan)
        this.spinnerDesa = v.findViewById(R.id.spinner_desa)
        this.spinnerDusun = v.findViewById(R.id.spinner_dusun)
        this.spinnerRt = v.findViewById(R.id.spinner_rt)
        this.spinnerRw = v.findViewById(R.id.spinner_rw)
        this.spinnerPekerjaan = v.findViewById(R.id.spinner_pekerjaan)
        this.spinnerSubPekerjaan1 = v.findViewById(R.id.spinner_subpekerjaan1)
        this.spinnerSubPekerjaan2 = v.findViewById(R.id.spinner_subpekerjaan2)
        this.txtUmur = v.findViewById(R.id.txt_umur)
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
                TODO("Not yet implemented")
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
                TODO("Not yet implemented")
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
        loadPekerjaan()
    }

    private fun onSubmit() {

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