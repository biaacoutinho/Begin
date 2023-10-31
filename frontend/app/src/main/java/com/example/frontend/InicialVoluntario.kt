package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.VoluntarioService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class InicialVoluntario : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<ConexaoData>()
    private lateinit var adapter: ConexaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inical_voluntario)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addVoluntariosToList()

        adapter = ConexaoAdapter(mList, this, application)
        recyclerView.adapter = adapter

        val btnPerfil = findViewById<FloatingActionButton>(R.id.btnPerfilVoluntario)
        val btnSolicitacao = findViewById<LinearLayout>(R.id.secaoSolicitacao)
        val btnDoacao = findViewById<LinearLayout>(R.id.secaoDoacao)

        btnPerfil.setOnClickListener(){
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }

        btnSolicitacao.setOnClickListener(){
            startActivity(Intent(this, Solicitacoes::class.java))
        }

        btnDoacao.setOnClickListener(){
            val intent = Intent(this, Ong::class.java)
            intent.putExtra("ondeVeio", "voluntario")
            startActivity(intent)
        }
    }

    private fun addVoluntariosToList(){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(VoluntarioService::class.java)
        val callback = service.getVoluntarios()

        callback.enqueue(object : retrofit2.Callback<List<Voluntario>> {
            override fun onResponse(
                call: Call<List<Voluntario>>?,
                response: Response<List<Voluntario>>?
            ) {
                val voluntarioList = response?.body()

                if (voluntarioList != null) {
                    if (response!!.isSuccessful) {
                        for (voluntario in voluntarioList) {
                            mList.add(ConexaoData(voluntario.nome, voluntario.username, voluntario.idioma, voluntario.habilidade ))
                        }

                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<Voluntario>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}