package com.example.frontend.API.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.File

interface ProfilePictureService {
    @PUT("/refugiado/{username}")
    fun uploadPicture(@Path("username") username: String, @Body image: File): Call<ResponseBody>
}