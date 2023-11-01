package com.example.frontend

import ConexaoVoluntarioAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.ConexaoService
import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.API.services.VoluntarioService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class InicialVoluntario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inical_voluntario)

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilVoluntario)
        val btnSolicitacao = findViewById<LinearLayout>(R.id.secaoSolicitacao)
        val btnDoacao = findViewById<LinearLayout>(R.id.secaoDoacao)
        val btnConexoes = findViewById<LinearLayout>(R.id.secaoConexoes)

        btnPerfil.setOnClickListener(){
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }

        btnSolicitacao.setOnClickListener(){
            startActivity(Intent(this, Solicitacoes::class.java))
        }

        btnDoacao.setOnClickListener(){
            val intent = Intent(this, Doacao::class.java)
            intent.putExtra("ondeVeio", "voluntario")
            startActivity(intent)
        }

        btnConexoes.setOnClickListener(){
            startActivity(Intent(this, Conexoes::class.java))
        }
    }
}