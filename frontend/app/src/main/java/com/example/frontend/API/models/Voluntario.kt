package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Voluntario(
    @SerializedName("username")
    var username: String,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("senha")
    var senha: String,

    @SerializedName("idioma")
    var idioma: String,

    @SerializedName("cpf")
    var cpf: String,

    @SerializedName("telefone")
    var telefone: String,

    @SerializedName("habilidade")
    var habilidade: String,

    @SerializedName("email")
    var email: String? = null
) : Serializable
