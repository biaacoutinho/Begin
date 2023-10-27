package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InicialRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_refugiado)

        val btnPerfilRefugiado = findViewById<FloatingActionButton>(R.id.btnPerfilRefugiado)

        val secaoDoacao = findViewById<LinearLayout>(R.id.secaoDoacao)
        val btnDoacao = findViewById<Button>(R.id.btnDoacao)

        val secaoConexao = findViewById<LinearLayout>(R.id.secaoConexaoVoluntarios)
        val btnConexao = findViewById<Button>(R.id.btnConectaVol)

        btnPerfilRefugiado.setOnClickListener() {
            val gUser = application as GlobalUser
            val user = gUser.getGlobalRefugiado()

            if (user != null)
                startActivity(Intent(this, perfilRefugiado::class.java))
            else
                startActivity(Intent(this, Login::class.java))
        }

        btnDoacao.setOnClickListener() {
            val intent = Intent(this, Ong::class.java)
            intent.putExtra("ondeVeio", "refugiado")
            startActivity(intent)
        }
        secaoDoacao.setOnClickListener() {
            val intent = Intent(this, Doacao::class.java)
            intent.putExtra("ondeVeio", "refugiado")
            startActivity(intent)
        }

        secaoConexao.setOnClickListener() {
            startActivity(Intent(this, ConexoesVoluntario::class.java))
        }
        btnConexao.setOnClickListener() {
            startActivity(Intent(this, ConexoesVoluntario::class.java))
        }
    }
}