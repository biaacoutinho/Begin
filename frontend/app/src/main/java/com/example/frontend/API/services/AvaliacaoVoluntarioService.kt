package com.example.frontend.API.services

import com.example.frontend.API.models.AvaliacaoVoluntario
import com.example.frontend.API.models.Conexao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AvaliacaoVoluntarioService {
    @GET("/avaliacoesVoluntario/{username}")
    fun getAvaliacoes(@Path("username") username: String): Call<List<AvaliacaoVoluntario>>

    @GET("/avaliacaoVoluntario/{role}/{username}")
    fun getAvaliacao(@Path("role") role: String, @Path("username") username: String): Call<List<AvaliacaoVoluntario>>

    @PUT("/avaliacaoVoluntario")
    fun postAvaliacao(@Body conexao: Conexao): Call<List<AvaliacaoVoluntario>>

    @POST("/avaliacaoVoluntario/{id}")
    fun putAvaliacao(@Path("id") id: Int, @Body conexao: Conexao): Call<List<AvaliacaoVoluntario>>

    @DELETE("/avaliacaoVoluntario/{usernameRefugiado}/{usernameVoluntario}")
    fun deleteAvaliacao(@Path("usernameRefugiado") usernameRefugiado: String, @Path("usernameVoluntario") usernameVoluntario: String): Call<List<AvaliacaoVoluntario>>
}