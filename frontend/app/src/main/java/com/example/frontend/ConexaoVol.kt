package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.VoluntarioService
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ConexaoVol : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var mList = ArrayList<ConexaoData>()
    private lateinit var adapter: ConexaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = ConexaoAdapter(mList)
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
            val filteredList = ArrayList<ConexaoData>()
            for(i in mList){
                if(i.user.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || i.nomeUsuario.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
                    || i.habilidadesUser.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) || i.idiomaUser.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) ){
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
    private fun addDataToList(){
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