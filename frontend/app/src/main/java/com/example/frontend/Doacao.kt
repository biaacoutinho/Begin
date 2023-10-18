package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Doacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_ongs)

        val ondeVeio: String? = intent.getStringExtra("ondeVeio");

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilDoacao)

        btnPerfil.setOnClickListener(){
            if (ondeVeio == "voluntario")
                startActivity(Intent(this, PerfilVoluntario::class.java))
            else
                startActivity(Intent(this, perfilRefugiado::class.java))
        }
    }
}