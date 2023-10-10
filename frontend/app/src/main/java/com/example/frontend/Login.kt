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
            val intent = Intent(this, CadastroRefugiado::class.java)
            startActivity(intent)
        })

        btnLogin.setOnClickListener {
            if (txtUsername.text.toString() != "")
            {
                Log.d("username invalido", "digite username")
                if (txtSenha.text.toString() != "")
                {
                    verificarLoginRefugiado(txtUsername.text.toString(), txtSenha.text.toString())
                }
                else
                    Toast.makeText(this@Login, "Digite uma senha para continuar", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this@Login, "Digite um username para continuar", Toast.LENGTH_LONG).show()
        }
    }

    fun verificarLoginRefugiado(username: String, senha: String) {
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(RefugiadoService::class.java)

        val callback = service.getRefugiado(username)

        callback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(
                call: Call<List<Refugiado>>?,
                response: Response<List<Refugiado>>?
            ) {
                if (response!!.isSuccessful) {
                    val refugiadosList = response.body()

                    if (refugiadosList != null) {
                        for (refugiado in refugiadosList) {
                            if (refugiado.username == username) {
                                if (refugiado.senha == senha)
                                {
                                    val gUser = application as GlobalUser
                                    Log.d("refugiado", refugiado.nome)
                                    gUser.setGlobalRefugiado(refugiado)

                                    Toast.makeText(this@Login, "Login feito com sucesso", Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this@Login, InicialRefugiado::class.java))
                                }

                                else
                                    Toast.makeText(this@Login, "Senha Incorreta", Toast.LENGTH_LONG).show()
                            }
                            else
                                Toast.makeText(this@Login, "Usuário Inexistente", Toast.LENGTH_LONG).show()
                        }
                        verificarLoginVoluntario(username, senha)
                    }


                } else {
                    val errorMessage = response?.errorBody().toString()
                    Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Refugiado>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Toast.makeText(this@Login, messageProblem, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun verificarLoginVoluntario(username: String, senha: String) {
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(VoluntarioService::class.java)
        val callback = service.getVoluntario(username)

        callback.enqueue(object : retrofit2.Callback<List<Voluntario>> {
            override fun onResponse(
                call: Call<List<Voluntario>>?,
                response: Response<List<Voluntario>>?
            ) {
                if (response!!.isSuccessful) {
                    val voluntariosList = response.body()

                    if (voluntariosList != null) {
                        for (voluntario in voluntariosList) {
                            if (voluntario.username == username) {
                                if (voluntario.senha == senha) {
                                    Toast.makeText(this@Login, "Login feito com sucesso", Toast.LENGTH_LONG).show()
                                    val gUser = application as GlobalUser
                                    gUser.setGlobalVoluntario(voluntario)
                                    startActivity(Intent(this@Login, InicialVoluntario::class.java))
                                }
                                else
                                    Toast.makeText(this@Login, "Senha Incorreta", Toast.LENGTH_LONG).show()
                            }
                            else
                                Toast.makeText(this@Login, "Usuário Inexistente", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    val errorMessage = response?.errorBody().toString()
                    Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Voluntario>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Toast.makeText(this@Login, messageProblem, Toast.LENGTH_LONG).show()
            }
        })
    }
}