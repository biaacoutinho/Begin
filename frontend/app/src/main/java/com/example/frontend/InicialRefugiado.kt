package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class InicialRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_refugiado)

        val btnPerfilRefugiado = findViewById<Button>(R.id.btnPerfilRefugiado)

        btnPerfilRefugiado.setOnClickListener(){
            val gUser = application as GlobalUser
            val user = gUser.getGlobalRefugiado()
            Log.d("usuario", user.nome)
            if (user != null)
                startActivity(Intent(this, perfilRefugiado::class.java))
            else
                startActivity(Intent(this, Login::class.java))
        }

    }
}