package com.example.frontend.API

import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.API.services.VoluntarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://177.220.18.8:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        //
        //177.220.18.18
    }
}