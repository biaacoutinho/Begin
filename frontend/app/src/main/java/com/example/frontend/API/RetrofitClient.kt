package com.example.frontend.API

import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.API.services.VoluntarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.106:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        //192.168.0.106
        //177.220.18.18
        //177.220.18.8
        //177.220.18.10
    }
}