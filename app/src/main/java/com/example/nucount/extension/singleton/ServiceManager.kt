package com.example.nucount.extension.singleton

import com.example.nucount.core.constant.API
import com.example.nucount.core.constant.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceManager {

    companion object {

        private var INSTANCE: Service? = null

        fun getInstance(): Service {
            if (INSTANCE == null) {
                val retrofit = Retrofit
                    .Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                this.INSTANCE = retrofit.create(Service::class.java)
                return this.INSTANCE!!
            }

            return this.INSTANCE!!
        }
    }
}