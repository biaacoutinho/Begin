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
        val url : String? = "https://doar.acnur.org/page/ACNURBR/doe/general?gad_source=1&gclid=CjwKCAjwysipBhBXEiwApJOcux0-SXxnmgn4IzHNW3rOGtBJ4fqxw41tkjKUTHuoQ9vKYuFR82c4OBoCAlQQAvD_BwE&gclsrc=aw.ds"

        val ondeVeio: String? = intent.getStringExtra("ondeVeio");

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilDoacao)

        val ongOnu = findViewById<LinearLayout>(R.id.ong1)

        btnPerfil.setOnClickListener(){
            if (ondeVeio == "voluntario")
                startActivity(Intent(this, PerfilVoluntario::class.java))
            else
                startActivity(Intent(this, perfilRefugiado::class.java))
        }

        ongOnu.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}