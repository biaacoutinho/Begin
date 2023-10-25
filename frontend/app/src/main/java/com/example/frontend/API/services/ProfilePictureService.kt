package com.example.frontend.API.services

import android.media.Image
import com.example.frontend.API.models.ProfilePicture
import com.example.frontend.API.models.Refugiado
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.File

interface ProfilePictureService {
    @PUT("/upload/{username}")
    fun uploadPicture(@Path("username") username: String, @Body profilePicture : ProfilePicture): Call<ResponseBody>
}