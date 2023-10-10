package com.example.frontend

import android.app.Application
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario

class GlobalUser: Application() {
    public lateinit var voluntario : Voluntario;
    public lateinit var refugiado : Refugiado;

    fun getGlobalVoluntario(): Voluntario? {
        if (this::voluntario.isInitialized)
            return voluntario
        else
            return null
    }
    fun setGlobalVoluntario(volun: Voluntario) {
        voluntario = volun
    }

    fun getGlobalRefugiado(): Refugiado? {
        if (this::refugiado.isInitialized)
            return refugiado
        else
            return null
    }
    fun setGlobalRefugiado(ref: Refugiado) {
        refugiado = ref
    }
}