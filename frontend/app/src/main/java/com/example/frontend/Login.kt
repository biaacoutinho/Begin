package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.API.services.VoluntarioService
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvCadastrar = findViewById<TextView>(R.id.tvCadastrar)
        val btnLogin = findViewById<Button>(R.id.login_button)
        val txtUsername = findViewById<EditText>(R.id.login_username)
        val txtSenha = findViewById<EditText>(R.id.login_password)

        tvCadastrar.setOnClickListener({
            val intent = Intent(this, activty_logon::class.java)
            startActivity(intent)
        })

        Log.d("MinhaTag", "Antes de chamar o serviço Retrofit")

        btnLogin.setOnClickListener {
            val retrofitClient = RetrofitClient.getRetrofit()
            val service = retrofitClient.create(RefugiadoService::class.java)
            val usernameToCheck = txtUsername.text.toString()
            val passwordToCheck = txtSenha.text.toString()

            val callback = service.getRefugiado(usernameToCheck)

            Log.d("username digitado", usernameToCheck)

            callback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
                override fun onResponse(
                    call: Call<List<Refugiado>>?,
                    response: Response<List<Refugiado>>?
                ) {
                    if (response!!.isSuccessful) {
                        val refugiadosList = response.body()

                        if (refugiadosList != null) {
                            Log.d("entrou aqui", "aff")
                            for (refugiado in refugiadosList) {
                                if (refugiado.username == usernameToCheck) {
                                    Log.d("senha", refugiado.senha)
                                    Log.d("senha escrita", passwordToCheck)
                                    if (refugiado.senha == passwordToCheck)
                                        Toast.makeText(this@Login, "Login feito com sucesso", Toast.LENGTH_LONG).show()
                                    else
                                        Toast.makeText(this@Login, "senha incorreta", Toast.LENGTH_LONG).show()
                                }
                                else
                                    Toast.makeText(this@Login, "usuário inexistente", Toast.LENGTH_LONG).show()
                            }
                                val service = retrofitClient.create(VoluntarioService::class.java)
                                val callback = service.getVoluntario(usernameToCheck)
                                callback.enqueue(object : retrofit2.Callback<List<Voluntario>> {
                                    override fun onResponse(
                                        call: Call<List<Voluntario>>?,
                                        response: Response<List<Voluntario>>?
                                    ) {
                                        if (response!!.isSuccessful) {
                                            val voluntariosList = response.body()

                                            if (voluntariosList != null) {
                                                for (refugiado in voluntariosList) {
                                                    if (refugiado.username == usernameToCheck) {
                                                        Log.d("senha", refugiado.senha)
                                                        Log.d("senha escrita", passwordToCheck)
                                                        if (refugiado.senha == passwordToCheck)
                                                            Toast.makeText(this@Login, "Login feito com  - Voluntario", Toast.LENGTH_LONG).show()
                                                        else
                                                            Toast.makeText(this@Login, "senha incorreta", Toast.LENGTH_LONG).show()
                                                    }
                                                    else
                                                        Toast.makeText(this@Login, "usuário inexistente", Toast.LENGTH_LONG).show()
                                                }
                                            }

                                        } else {
                                            val errorMessage = response?.errorBody().toString()
                                            Log.d("erro aqui", errorMessage)
                                            Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Voluntario>>?, t: Throwable?) {
                                        Toast.makeText(this@Login, "Aqui", Toast.LENGTH_LONG).show()
                                        val messageProblem: String = t?.message.toString()
                                        Log.d("erro", messageProblem)
                                        Toast.makeText(this@Login, messageProblem, Toast.LENGTH_LONG).show()
                                    }
                                })
                        }


                    } else {
                        val errorMessage = response?.errorBody().toString()
                        Log.d("erro aqui", errorMessage)
                        Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<Refugiado>>?, t: Throwable?) {
                    Toast.makeText(this@Login, "Aqui", Toast.LENGTH_LONG).show()
                    val messageProblem: String = t?.message.toString()
                    Log.d("erro", messageProblem)
                    Toast.makeText(this@Login, messageProblem, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}