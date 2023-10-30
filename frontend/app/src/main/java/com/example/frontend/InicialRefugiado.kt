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

        val secaoVoluntarios = findViewById<LinearLayout>(R.id.secaoVoluntarios)
        val btnVoluntario = findViewById<Button>(R.id.btnVoluntario)

        val secaoConexao = findViewById<LinearLayout>(R.id.secaoConexaoVoluntarios)
        val btnConexao = findViewById<Button>(R.id.btnConectaVol)

        val secaoAbrigo = findViewById<LinearLayout>(R.id.secaoAbrigo)
        val btnAbrigo = findViewById<Button>(R.id.btnAbrigo)

        btnPerfilRefugiado.setOnClickListener() {
            val gUser = application as GlobalUser
            val user = gUser.getGlobalRefugiado()

            if (user != null)
                startActivity(Intent(this, perfilRefugiado::class.java))
            else
                startActivity(Intent(this, Login::class.java))
        }

        btnAbrigo.setOnClickListener() {
            val intent = Intent(this, TelaMapa::class.java)
            startActivity(intent)
        }

        secaoAbrigo.setOnClickListener() {
            val intent = Intent(this, TelaMapa::class.java)
            startActivity(intent)
        }

        btnDoacao.setOnClickListener() {
            startActivity(Intent(this, Ong::class.java))
        }
        secaoDoacao.setOnClickListener() {
            startActivity(Intent(this, Ong::class.java))
        }

        secaoVoluntarios.setOnClickListener() {
            val intent = Intent(this, ConexaoVol::class.java)
            intent.putExtra("qualExibicao", "voluntariosConectados")
            startActivity(intent)
        }
        btnVoluntario.setOnClickListener() {
            val intent = Intent(this, ConexaoVol::class.java)
            intent.putExtra("qualExibicao", "voluntariosConectados")
            startActivity(intent)
        }

        secaoConexao.setOnClickListener() {
            val intent = Intent(this, ConexaoVol::class.java)
            intent.putExtra("qualExibicao", "criarConexao")
            startActivity(intent)
        }

        btnConexao.setOnClickListener() {
            val intent = Intent(this, ConexaoVol::class.java)
            intent.putExtra("qualExibicao", "criarConexao")
            startActivity(intent)
        }
    }
}