package com.example.frontend

import ConexaoVoluntarioAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.ConexaoService
import com.example.frontend.API.services.RefugiadoService
import retrofit2.Call
import retrofit2.Response

class Conexoes: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<SolicitacoesData>()
    private lateinit var adapter: ConexaoVoluntarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()!!

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addConexoesToList(user.username)

        adapter = ConexaoVoluntarioAdapter(mList, this)
        recyclerView.adapter = adapter
    }

    private fun addConexoesToList(voluntario: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)
        val callback = service.getConexao("voluntario", voluntario)

        callback.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                val conexoesList = response?.body()

                if (conexoesList != null) {
                    if (response!!.isSuccessful) {
                        for (conexao in conexoesList) {
                            if (!conexao.pendente)
                            {
                                val refugiadoService = retrofitClient.create(RefugiadoService::class.java)
                                val refCallback = refugiadoService.getRefugiado(conexao.usernameRefugiado)

                                refCallback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
                                    override fun onResponse(
                                        call: Call<List<Refugiado>>?,
                                        response: Response<List<Refugiado>>?
                                    ) {
                                        val refugiadoList = response?.body()
                                        if (refugiadoList != null) {
                                            if (response!!.isSuccessful) {
                                                Log.d("refugiado nome", refugiadoList[0].nome)
                                                mList.add(SolicitacoesData(refugiadoList[0].nome, refugiadoList[0].username, refugiadoList[0].idioma, refugiadoList[0].paisOrigem ))

                                            } else {
                                                val errorMessage = response?.errorBody().toString()
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Refugiado>>, t: Throwable) {
                                        TODO("Not yet implemented")
                                    }

                                })
                            }
                        }

                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<Conexao>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}