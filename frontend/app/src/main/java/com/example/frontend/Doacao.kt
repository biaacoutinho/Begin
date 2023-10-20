package com.example.frontend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Doacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_ongs)
        val urlOnu : String? = "https://doar.acnur.org/page/ACNURBR/doe/general"
        val urlMissaoPaz : String? = "https://missaonspaz.org/doar/"
        val urlAfricaCoracao : String? = "https://sedoar.com.br/entidades/pdmig-africa-do-coracao"
        val urlMulherImigrante : String? = "https://www.mulherimigrante.org/"

        val ondeVeio: String? = intent.getStringExtra("ondeVeio");

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilDoacao)

        val ongOnu = findViewById<LinearLayout>(R.id.ong1)
        val ongMissaoPaz = findViewById<LinearLayout>(R.id.ong2)
        val ongAfricaCoracao = findViewById<LinearLayout>(R.id.ong3)
        val ongMulherImigrante = findViewById<LinearLayout>(R.id.ong4)

        btnPerfil.setOnClickListener(){
            if (ondeVeio == "voluntario")
                startActivity(Intent(this, PerfilVoluntario::class.java))
            else
                startActivity(Intent(this, perfilRefugiado::class.java))
        }

        ongOnu.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(urlOnu)
            startActivity(i)
        }

        ongMissaoPaz.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(urlMissaoPaz)
            startActivity(i)
        }

        ongAfricaCoracao.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(urlAfricaCoracao)
            startActivity(i)
        }

        ongMulherImigrante.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(urlMulherImigrante)
            startActivity(i)
        }
    }
}