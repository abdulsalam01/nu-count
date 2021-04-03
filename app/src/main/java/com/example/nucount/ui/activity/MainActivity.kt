package com.example.nucount.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.nucount.R
import com.example.nucount.core.helper.GlobalHelper
import com.example.nucount.core.session.Session
import com.example.nucount.extension.database.MemberOperation
import com.example.nucount.ui.fragment.InputFragment
import com.example.nucount.ui.fragment.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            R.id.notification_menu -> fragment = NotificationFragment()
            R.id.exit_menu -> {
                Session.clearUser(this)
                return true
            }
        }

        return GlobalHelper.loadFragment(this, fragment)
    }
}