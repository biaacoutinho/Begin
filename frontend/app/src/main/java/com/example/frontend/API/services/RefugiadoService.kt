package com.example.frontend.API.services

import com.example.frontend.API.models.Refugiado
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RefugiadoService {
    @GET("/refugiados")
    fun getRefugiados(): Call<List<Refugiado>>

    @GET("/refugiado/{username}")
    fun getRefugiado(username:String): Call<List<Refugiado>>

    @POST("/refugiado")
    fun postRefuagiado(refugiado: Refugiado): Call<List<Refugiado>>

    @PUT("/refugiado/{username}")
    fun putRefugido(username: String, refugiado: Refugiado): Call<Refugiado>

    @DELETE("/refugiado/{usrename}")
    fun deleteRefugiado(username: String): Call<Refugiado>
}