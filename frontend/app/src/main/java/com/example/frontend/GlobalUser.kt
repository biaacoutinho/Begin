package com.example.frontend

import android.app.Application
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario

class GlobalUser: Application() {
    public var voluntario: Voluntario? = null
    public var refugiado : Refugiado? = null

    fun getGlobalVoluntario(): Voluntario? {
        if (this.voluntario != null)
            return voluntario
        else
            return null
    }
    fun setGlobalVoluntario(volun: Voluntario?) {
        if(volun != null)
            voluntario = volun
        else
            voluntario = null
    }

    fun getGlobalRefugiado(): Refugiado? {
        if (this.refugiado != null)
            return refugiado
        else
            return null
    }
    fun setGlobalRefugiado(ref: Refugiado?) {
        refugiado = ref
    }
}