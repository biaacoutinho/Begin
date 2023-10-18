package com.example.frontend

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class perfilRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_refugiado)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomas)
        val tvPais = findViewById<TextView>(R.id.tvPais)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefone)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val btnDeslogin = findViewById<FloatingActionButton>(R.id.btLogOut)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalRefugiado()

        btnDeslogin.setOnClickListener(){
            val gUser = application as GlobalUser
            gUser.setGlobalRefugiado(null)

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        tvIdiomas.text = user?.idioma
        tvPais.text = user?.paisOrigem

        if (user?.telefone != null)
            tvTelefone.text = user?.telefone
        else
            tvTelefone.text = "Nenhum telefone foi cadastrado"

        if (user?.email != null)
            tvEmail.text = user?.email
        else
            tvEmail.text = "Nenhum email foi cadastrado"
    }
}