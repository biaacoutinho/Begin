package com.example.frontend

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

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

        val gUser = application as GlobalUser
        val user = gUser.getGlobalRefugiado()

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