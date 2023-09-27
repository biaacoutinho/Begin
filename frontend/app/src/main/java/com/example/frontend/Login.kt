package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvCadastrar = findViewById<TextView>(R.id.tvCadastrar)

        tvCadastrar.setOnClickListener({
            val intent = Intent(this, activty_logon::class.java)
            startActivity(intent)
        })
    }
}