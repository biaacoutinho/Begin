package com.example.frontend

import android.app.Application
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario

class GlobalUser: Application() {
    public lateinit var voluntario : Voluntario;
    public lateinit var refugiado : Refugiado;

    fun getGlobalVoluntario(): Voluntario {
        return voluntario
    }
    fun setGlobalVoluntario(volun: Voluntario) {
        voluntario = volun
    }

    fun getGlobalRefugiado(): Refugiado {
        return refugiado
    }
    fun setGlobalRefugiado(ref: Refugiado) {
        refugiado = ref
    }
}