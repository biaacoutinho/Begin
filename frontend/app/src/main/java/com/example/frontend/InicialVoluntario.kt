package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.API.models.Voluntario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InicialVoluntario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inical_voluntario)

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilVoluntario)
        val btnSolicitacao = findViewById<LinearLayout>(R.id.secaoSolicitacao)
        val btnDoacao = findViewById<LinearLayout>(R.id.secaoDoacao)

        btnPerfil.setOnClickListener(){
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }

        btnSolicitacao.setOnClickListener(){
            startActivity(Intent(this, SolicitacaoRefugiado::class.java))
        }

        btnDoacao.setOnClickListener(){
            val intent = Intent(this, Doacao::class.java)
            intent.putExtra("ondeVeio", "voluntario")
            startActivity(intent)
        }


    }
}