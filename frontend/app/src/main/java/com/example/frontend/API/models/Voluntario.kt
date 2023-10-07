package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName

data class Voluntario(
    @SerializedName("username")
    val username: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("senha")
    val senha: String,

    @SerializedName("idioma")
    val idioma: String,

    @SerializedName("telefone")
    val telefone: String,

    @SerializedName("habilidade")
    val habilidade: String,

    @SerializedName("email")
    val email: String = ""
)
