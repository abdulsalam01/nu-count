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
    const val USER_NAME = "user_name"
    const val USER_SURNAME = "user_surname"
    const val USER_LEVEL = "user_level"
    const val USER_TASK = "user_task"

    fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    fun rememberUser(context: Context, user: User) {
        val editor = getPreferences(context).edit()

        editor.putBoolean(LOGGED_IN, true)
        editor.putInt(USER_ID, user.id_user)
        editor.putString(USER_NAME, user.nama)
        editor.putString(USER_SURNAME, user.username)
        editor.putInt(USER_LEVEL, user.level)
        editor.putString(USER_TASK, user.tb_tugas)

        editor.apply()

        GlobalHelper.changeActivity(context, MainActivity())
    }

    fun isLoggedIn(context: Context) : Boolean {
        return getPreferences(context).getBoolean(LOGGED_IN, false)
    }

    fun getCurrentUser(context: Context) : User {
        val sharePreferenceManager = getPreferences(context)

        return User(sharePreferenceManager.getInt(USER_ID, -1),
            sharePreferenceManager.getString(USER_NAME, null)!!,
            sharePreferenceManager.getString(USER_SURNAME, null)!!,
            sharePreferenceManager.getInt(USER_LEVEL, -1),
            sharePreferenceManager.getString(USER_TASK, null)!!)
    }

    fun clearUser(context: Context) {
        val editor = getPreferences(context).edit()

        editor.clear()
        editor.apply()

        GlobalHelper.changeActivity(context, LoginActivity())
    }
}