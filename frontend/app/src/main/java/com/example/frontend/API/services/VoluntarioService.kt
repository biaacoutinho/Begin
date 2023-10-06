package com.example.frontend.API.services

import com.example.frontend.API.models.Voluntario
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VoluntarioService {
    @GET("/voluntarios")
    fun getVoluntarios(): Call<List<Voluntario>>

    @GET("/voluntario/{username}")
    fun getVoluntario(@Path("username") username: String): Call<List<Voluntario>>

    @POST("/voluntario")
    fun postVoluntario(refugiado: Voluntario): Call<List<Voluntario>>

    @PUT("/voluntario/{username}")
    fun putVoluntario(username: String, voluntario: Voluntario): Call<Voluntario>

    @DELETE("/voluntario/{username}")
    fun deleteVoluntario(username: String): Call<Voluntario>
}