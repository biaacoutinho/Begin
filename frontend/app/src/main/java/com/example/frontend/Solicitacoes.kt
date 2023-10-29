package com.example.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Solicitacoes : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var mList = ArrayList<SolicitacoesData>()
    private lateinit var adapter: SolicitacoesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()!!

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList(user.username)
        adapter = SolicitacoesAdapter(mList, user)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = ArrayList<SolicitacoesData>()
            for(i in mList){
                if(i.nomeRefugiado.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
                    || i.idiomaRefugiado.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
                    || i.paisRefugiado?.lowercase(Locale.ROOT)!!.contains(query.lowercase(Locale.ROOT))
                    || i.userRefugiado.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this, "Dado não encontrado", Toast.LENGTH_SHORT).show()
            } else{
                adapter.setFilteredList((filteredList))
            }
        }
    }
    private fun addDataToList(voluntario: String){

        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)
        val callback = service.getConexao("voluntario", voluntario)

        Log.d("volun", voluntario)
        callback.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                val conexaoList = response?.body()

                if (conexaoList != null) {
                    if (response!!.isSuccessful) {
                        for (conexao in conexaoList) {
                            if(conexao.pendente)
                                getRefugiado(conexao.usernameRefugiado)
                        }

                    } else {
                        val errorMessage = response?.errorBody().toString()
                        Log.d("error", errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<List<Conexao>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getRefugiado(usernameRef: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(RefugiadoService::class.java)
        val callback = service.getRefugiado(usernameRef)

        Log.d("ref", usernameRef)

        callback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(
                call: Call<List<Refugiado>>?,
                response: Response<List<Refugiado>>?
            ) {
                val refugiadoList = response?.body()
                if (refugiadoList != null) {
                    if (response!!.isSuccessful) {
                        mList.add(SolicitacoesData(refugiadoList[0].nome, refugiadoList[0].username, refugiadoList[0].idioma, refugiadoList[0].paisOrigem ))

                    } else {
                        val errorMessage = response?.errorBody().toString()
                        Log.d("error", errorMessage)
                    }
                }
            }

            override fun onFailure(call: Call<List<Refugiado>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}