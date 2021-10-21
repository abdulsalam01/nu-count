package com.nu.nucount.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import com.nu.nucount.R
import com.nu.nucount.core.constant.Service
import com.nu.nucount.core.helper.GlobalHelper
import com.nu.nucount.core.session.Session
import com.nu.nucount.extension.singleton.ServiceManager
import com.nu.nucount.model.User
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
    private lateinit var progressBar: ProgressBar

    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkSession()

        this.btnLogin = this.findViewById(R.id.btn_login)
        this.txtUsername = this.findViewById(R.id.txt_username)
        this.txtPassword = this.findViewById(R.id.txt_password)
        this.progressBar = this.findViewById(R.id.pDialog)
        this.service = ServiceManager.getInstance()

        this.btnLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            login(username, password)
        }
    }

    private fun login(username: String, password: String): Unit {
        if (TextUtils.isEmpty(username)) {
            this.txtUsername.error = resources.getString(R.string.validation_username)
            this.txtUsername.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            this.txtPassword.setError(resources.getString(R.string.validation_password), null)
            this.txtPassword.requestFocus()
            return
        }

        this.progressBar.visibility = View.VISIBLE
        this.service.login(username, password).enqueue(object : Callback<User.Response> {

            override fun onFailure(call: Call<User.Response>, t: Throwable) {
                Snackbar.make(btnLogin, t.message.toString(), Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User.Response>, response: Response<User.Response>) {
                progressBar.visibility = View.GONE

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
        }
    }
}