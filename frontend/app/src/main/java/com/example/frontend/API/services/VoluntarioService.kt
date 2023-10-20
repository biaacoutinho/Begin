package com.example.frontend.API.services

import com.example.frontend.API.models.Voluntario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
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

    @PUT("/voluntario")
    fun postVoluntario(@Body voluntario: Voluntario?): Call<Voluntario>?

    @POST("/voluntario/{username}")
    fun putVoluntario(@Path("username") username: String, @Body voluntario: Voluntario): Call<List<Voluntario>>

    @DELETE("/voluntario/{username}")
    fun deleteVoluntario(username: String): Call<Voluntario>
}