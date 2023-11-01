package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName

data class AvaliacaoVoluntario (
    @SerializedName("id")
    var id: Int,

    @SerializedName("usernameRefugiado")
    var usernameRefugiado: String,

    @SerializedName("usernameVoluntario")
    var usernameVoluntario: String,

    @SerializedName("like")
    var like: Boolean,

    @SerializedName("dislike")
    var dislike: Boolean,
)