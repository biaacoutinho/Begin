package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SolicitacaoRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitacao_refugiado)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltarSolicitacao)
        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilSolicitacao)

        btnVoltar.setOnClickListener(){
            startActivity(Intent(this, InicialVoluntario::class.java))
        }

        btnPerfil.setOnClickListener(){
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }
    }
}