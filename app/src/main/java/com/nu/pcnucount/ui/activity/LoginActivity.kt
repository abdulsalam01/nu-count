package com.nu.pcnucount.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nu.pcnucount.R
import com.nu.pcnucount.core.constant.Service
import com.nu.pcnucount.core.helper.GlobalHelper
import com.nu.pcnucount.core.session.Session
import com.nu.pcnucount.extension.singleton.ServiceManager
import com.nu.pcnucount.model.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: MaterialButton
    private lateinit var txtUsername: TextInputEditText
    private lateinit var txtPassword: TextInputEditText

    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkSession()

        this.btnLogin = this.findViewById(R.id.btn_login)
        this.txtUsername = this.findViewById(R.id.txt_username)
        this.txtPassword = this.findViewById(R.id.txt_password)
        this.service = ServiceManager.getInstance()

        this.btnLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            login(username, password)
        }
    }

    private fun login(username: String, password: String) : Unit {
        this.service.login(username, password).enqueue(object : Callback<User.Response> {

            override fun onFailure(call: Call<User.Response>, t: Throwable) {
                Snackbar.make(btnLogin, t.message.toString(), Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User.Response>, response: Response<User.Response>) {
                val success = response.body()!!.success
                val user = response.body()?.data

                if (success) {
                    Session.rememberUser(applicationContext, user!!)
                }

                Snackbar.make(btnLogin, success.toString(), Snackbar.LENGTH_SHORT).show()
            }

        })
    }

    private fun checkSession(): Unit {
        if (Session.isLoggedIn(this)) {
            GlobalHelper.changeActivity(this, MainActivity())
            finish()
        }
    }
}