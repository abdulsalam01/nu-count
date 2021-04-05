package com.example.nucount.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.nucount.R
import com.example.nucount.core.constant.Service
import com.example.nucount.extension.singleton.ServiceManager
import com.example.nucount.model.*
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var btnDynamicForm: Button
    private lateinit var service: Service

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

        this.btnDynamicForm = v.findViewById(R.id.btn_dynamic_form)
        this.spinnerStatus = v.findViewById(R.id.spinner_status)
        this.spinnerJenisKelamin = v.findViewById(R.id.spinner_jenis_kelamin)
        this.spinnerKabupaten = v.findViewById(R.id.spinner_kabupaten)
        this.spinnerKecamatan = v.findViewById(R.id.spinner_kecamatan)
        this.spinnerDesa = v.findViewById(R.id.spinner_desa)
        this.spinnerDusun = v.findViewById(R.id.spinner_dusun)
        this.spinnerRt = v.findViewById(R.id.spinner_rt)
        this.spinnerRw = v.findViewById(R.id.spinner_rw)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.service = ServiceManager.getInstance()

        initLoad()

        this.btnDynamicForm.setOnClickListener {
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
                Snackbar.make(btnDynamicForm, t.message.toString(), Snackbar.LENGTH_LONG).show()
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
                Snackbar.make(btnDynamicForm, t.message.toString(), Snackbar.LENGTH_LONG).show()
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
                Snackbar.make(btnDynamicForm, t.message.toString(), Snackbar.LENGTH_LONG).show()
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
                Snackbar.make(btnDynamicForm, t.message.toString(), Snackbar.LENGTH_LONG).show()
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
                Snackbar.make(btnDynamicForm, t.message.toString(), Snackbar.LENGTH_LONG).show()
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
}