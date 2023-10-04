package com.example.frontend.API.services

import com.example.frontend.API.models.VoluntarioModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface VoluntarioService {
    @GET("/voluntarios")
    fun getVoluntarios(): Call<List<VoluntarioModel>>

    @GET("/voluntario/{username}")
    fun getVoluntario(username:String): Call<List<VoluntarioModel>>

    @POST("/voluntario")
    fun postVoluntario(refugiado: VoluntarioModel): Call<List<VoluntarioModel>>

    @PUT("/voluntario/{username}")
    fun putVoluntario(username: String, voluntario: VoluntarioModel): Call<VoluntarioModel>

    @DELETE("/voluntario/{usrename}")
    fun deleteVoluntario(username: String): Call<VoluntarioModel>
}