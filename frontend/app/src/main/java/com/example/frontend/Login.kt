package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.RefugiadoService
import retrofit2.Call
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvCadastrar = findViewById<TextView>(R.id.tvCadastrar)

        tvCadastrar.setOnClickListener({
            val intent = Intent(this, activty_logon::class.java)
            startActivity(intent)
        })

        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(RefugiadoService::class.java)
        val callback = service.getRefugiados()

        callback.enqueue(object: retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(call: Call<List<Refugiado>>?, response: Response<List<Refugiado>>?) {
                if (response!!.isSuccessful) {
                    Toast.makeText(this@Login, response.body().toString(), Toast.LENGTH_LONG).show()
                } else {
                    val errorMessage = response?.errorBody().toString()
                    Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Refugiado>>?, t: Throwable?) {
                Toast.makeText(this@Login, "Aqui", Toast.LENGTH_LONG).show()
                val messageProblem: String = t?.message.toString()
                Toast.makeText(this@Login, messageProblem, Toast.LENGTH_LONG).show()
            }
        })
    }
}