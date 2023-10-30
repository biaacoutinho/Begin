package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName

data class Conexao (
    @SerializedName("id")
    val id: Int,

    @SerializedName("usernameRefugiado")
    val usernameRefugiado: String,

    @SerializedName("usernameVoluntario")
    val usernameVoluntario: String,

    @SerializedName("pendente")
    val pendente: Boolean,
)