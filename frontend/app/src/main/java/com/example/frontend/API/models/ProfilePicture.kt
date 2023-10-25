package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName

data class ProfilePicture (
    @SerializedName("base64Image")
    val base64Image: String
)

