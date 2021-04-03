package com.example.nucount.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nucount.R
import com.example.nucount.core.helper.ConnectionChecker
import com.example.nucount.core.helper.GlobalHelper
import com.example.nucount.core.session.Session
import com.example.nucount.model.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: MaterialButton
    private lateinit var txtUsername: TextInputEditText
    private lateinit var txtPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkSession()

        this.btnLogin = this.findViewById(R.id.btn_login)
        this.txtUsername = this.findViewById(R.id.txt_username)
        this.txtPassword = this.findViewById(R.id.txt_password)

        this.btnLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()
            val result = login(username, password)

            if (result) Session.rememberUser(this, User(username.length))
        }
    }

    private fun login(username: String, password: String) : Boolean {
        return true;
    }

    private fun checkSession(): Unit {
        if (Session.isLoggedIn(this)) GlobalHelper.changeActivity(this, MainActivity())
    }
}