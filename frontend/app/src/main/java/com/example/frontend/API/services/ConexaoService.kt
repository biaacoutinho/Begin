package com.example.frontend.API.services

import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Refugiado
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConexaoService {

    @GET("/conexoes")
    fun getConexoes(): Call<List<Conexao>>

    @GET("/conexao/{role}/{username}")
    fun getConexao(@Path("role") role: String, @Path("username") username: String): Call<List<Conexao>>

    @PUT("/conexao")
    fun postConexao(@Body conexao: Conexao): Call<List<Conexao>>

    @POST("/conexao/{usernameRefugiado}/{usernameVoluntario}")
    fun putConexao(@Path("usernameRefugiado") usernameRefugiado: String, @Path("usernameVoluntario") usernameVoluntario: String, conexao: Conexao): Call<List<Conexao>>

    @DELETE("/conexao/{usernameRefugiado}/{usernameVoluntario}")
    fun deleteConexao(@Path("usernameRefugiado") usernameRefugiado: String, @Path("usernameVoluntario") usernameVoluntario: String): Call<Conexao>
}