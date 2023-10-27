package com.example.frontend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class Ong : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var mList = ArrayList<OngData>()
    private lateinit var adapter: OngsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = OngsAdapter(mList)
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
            val filteredList = ArrayList<OngData>()
            for(i in mList){
                if(i.title.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
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
        mList.add(OngData("Agência da ONU para refugiados no Brasil", R.drawable.ong_1))
        mList.add(OngData("A Missão Paz", R.drawable.ong_2))
        mList.add(OngData("África do Coração PDMIG", R.drawable.ong_3))
        mList.add(OngData("Centro da Mulher Imigrante e Refugiada", R.drawable.ong_4))

    }
}