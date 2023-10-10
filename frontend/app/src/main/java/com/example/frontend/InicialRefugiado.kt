package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InicialRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_refugiado)

        val btnPerfilRefugiado = findViewById<FloatingActionButton>(R.id.btnPerfilRefugiado)

        btnPerfilRefugiado.setOnClickListener(){
            val gUser = application as GlobalUser
            val user = gUser.getGlobalRefugiado()

            if (user != null)
                startActivity(Intent(this, perfilRefugiado::class.java))
            else
                startActivity(Intent(this, Login::class.java))
        }

    }
}