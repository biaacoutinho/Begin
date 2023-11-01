package com.example.frontend.API.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Refugiado (
    @SerializedName("username")
    var username: String,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("senha")
    var senha: String,

    @SerializedName("idioma")
    var idioma: String,

    @SerializedName("paisDeOrigem")
    var paisOrigem: String? = null,

    @SerializedName("telefone")
    var telefone: String? = null,

    @SerializedName("email")
    var email: String? = null
    ) : Serializable