package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PerfilVoluntario: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_voluntario)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)

        btnVoltar.setOnClickListener(){
            startActivity(Intent(this, InicialVoluntario::class.java))
        }

        val tvNome = findViewById<TextView>(R.id.tvNomeVol)
        val tvUsername = findViewById<TextView>(R.id.tvUsernameVol)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomasVol)
        val tvHabilidade = findViewById<TextView>(R.id.tvHabilidadesVol)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefoneVol)
        val tvEmail = findViewById<TextView>(R.id.tvEmailVol)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        tvIdiomas.text = user?.idioma
        tvHabilidade.text = user?.habilidade
        tvTelefone.text = user?.telefone

        if (user?.email != null)
            tvEmail.text = user?.email
        else
            tvEmail.text = "Nenhum email foi cadastrado"
    }
}