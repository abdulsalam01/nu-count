package com.example.nucount.core.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.nucount.R

object GlobalHelper {

    fun changeActivity(context: Context, activity: Activity) : Unit {
        val i = Intent(context, activity::class.java)

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        context.startActivity(i)
        ((context) as Activity).finish()
    }

    fun loadFragment(context: Context, fragment: Fragment) : Boolean {
        ((context) as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

        return true
    }
}