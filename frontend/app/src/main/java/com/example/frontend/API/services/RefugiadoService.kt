package com.example.frontend.API.services

import com.example.frontend.API.models.Refugiado
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RefugiadoService {
    @GET("/refugiados")
    fun getRefugiados(): Call<List<Refugiado>>

    @GET("/refugiado/{username}")
    fun getRefugiado(@Path("username") username: String): Call<List<Refugiado>>

    @PUT("/refugiado")
    fun postRefugiado(@Body refugiado: Refugiado): Call<List<Refugiado>>

    @POST("/refugiado/{username}")
    fun putRefugido(username: String, refugiado: Refugiado): Call<List<Refugiado>>

    @DELETE("/refugiado/{username}")
    fun deleteRefugiado(username: String): Call<Refugiado>
}