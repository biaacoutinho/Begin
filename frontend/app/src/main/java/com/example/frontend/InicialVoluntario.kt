package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.API.models.Voluntario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InicialVoluntario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inical_voluntario)

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilVoluntario)

        btnPerfil.setOnClickListener(){
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }
    }
}