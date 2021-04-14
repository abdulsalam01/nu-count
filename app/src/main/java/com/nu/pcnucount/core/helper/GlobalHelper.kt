package com.nu.pcnucount.core.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nu.pcnucount.R

object GlobalHelper {

    fun changeActivity(context: Context, activity: Activity) : Unit {
        val i = Intent(context, activity::class.java)

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun loadFragment(context: Context, fragment: Fragment) : Boolean {
        ((context) as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

        return true
    }
}