package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val btnVoluntario = findViewById<Button>(R.id.btnVoluntario)
        val btnRefugiado = findViewById<Button>(R.id.btnRefugiado)

        tvLogin.setOnClickListener({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        })

        btnRefugiado.setOnClickListener({
            val intent = Intent(this, InicialRefugiado::class.java)
            startActivity(intent)
        })

        btnVoluntario.setOnClickListener({
            val intent = Intent( this, InicialVoluntario::class.java)
            startActivity(intent)
        })
    }
}