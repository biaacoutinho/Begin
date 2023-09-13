package com.example.begin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRefugiados()
        setContentView(R.layout.activity_main)
    }

    private fun getRefugiados() {
        lifecycleScope.launch {
            val client = getClient()
            val res = client.postgrest["Refugiado"].select()
            val data = res.decodeList<Refugiado>()
            Log.e("refugiados", data.toString())
        }
    }

    private fun getClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://mgngxqkbhyqgyysiunaq.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1nbmd4cWtiaHlxZ3l5c2l1bmFxIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTQ1MTk1MzksImV4cCI6MjAxMDA5NTUzOX0.gJfV4foZWTSoxWHiBAkAB6Kvu_q-Pt_r887IaLsnsi4"
        ) {
            install(Postgrest)
        }
    }
}

@kotlinx.serialization.Serializable
data class Refugiado (
    val username: String = "",
    val nome: String = "",
    val senha:  String = "",
    val idioma: String = "",
    val paisDeOrigem: String = "",
    val telefone: String = "",
    val email: String = ""
)