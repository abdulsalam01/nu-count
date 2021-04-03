package com.example.nucount.core.session

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.nucount.core.helper.GlobalHelper
import com.example.nucount.model.User
import com.example.nucount.ui.activity.LoginActivity
import com.example.nucount.ui.activity.MainActivity

object Session {

    const val LOGGED_IN = "logged_in"
    const val USER_ID = "user_id"

    fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    fun rememberUser(context: Context, user: User) {
        val editor = getPreferences(context).edit()

        editor.putBoolean(LOGGED_IN, true)
        editor.putInt(USER_ID, user.id)

        editor.apply()
        GlobalHelper.changeActivity(context, MainActivity())
    }

    fun getCurrentUser(context: Context) : User {
        val sharePreferenceManager = getPreferences(context)

        return User(sharePreferenceManager.getInt(USER_ID, -1))
    }

    fun isLoggedIn(context: Context) : Boolean {
        return getPreferences(context).getBoolean(LOGGED_IN, false)
    }

    fun clearUser(context: Context) {
        val editor = getPreferences(context).edit()

        editor.clear()
        editor.apply()

        GlobalHelper.changeActivity(context, LoginActivity())
    }
}