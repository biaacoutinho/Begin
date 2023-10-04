package com.example.frontend.API.services

import com.example.frontend.API.models.RefugiadoModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RefugiadoService {
    @GET("/refugiados")
    fun getRefugiados(): Call<List<RefugiadoModel>>

    @GET("/refugiado/{username}")
    fun getRefugiado(username:String): Call<List<RefugiadoModel>>

    @POST("/refugiado")
    fun postRefuagiado(refugiado: RefugiadoModel): Call<List<RefugiadoModel>>

    @PUT("/refugiado/{username}")
    fun putRefugido(username: String, refugiado: RefugiadoModel): Call<RefugiadoModel>

    @DELETE("/refugiado/{usrename}")
    fun deleteRefugiado(username: String): Call<RefugiadoModel>
}