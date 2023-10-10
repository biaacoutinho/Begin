package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class CadastroRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_refugiado)

        val tvLogin = findViewById<TextView>(R.id.tvCadastrar)
        val btnCadastrar = findViewById<Button>(R.id.login_button)
        val txtUsername = findViewById<EditText>(R.id.cadastro_usernameRef)
        val txtNome = findViewById<EditText>(R.id.cadastro_NomeRef)
        val txtSenha = findViewById<EditText>(R.id.cadastro_senhaRef)
        val txtConfirmSenha = findViewById<EditText>(R.id.cadastro_confSenhaRef)
        val txtIdioma = findViewById<EditText>(R.id.cadastro_IdiomasRef)
        val txtPaisOrigem = findViewById<EditText>(R.id.cadastro_paisOrigem)
        val txtTelefone = findViewById<EditText>(R.id.cadastro_telefone)
        val txtEmail = findViewById<EditText>(R.id.cadastro_email)

        tvLogin.setOnClickListener(){
            startActivity(Intent(this, Login::class.java))
        }

        btnCadastrar.setOnClickListener(){
            var username: String = txtUsername.text.toString()
            var nome: String = txtNome.text.toString()
            var senha: String = txtSenha.text.toString()
            var confirmeSenha: String = txtConfirmSenha.text.toString()
            var idioma: String = txtIdioma.text.toString()
            var paisOrigem: String = txtPaisOrigem.text.toString()
            var telefone: String = txtTelefone.text.toString()
            var email: String = txtEmail.text.toString()

            if (username != "" && nome != "" && senha != "" && confirmeSenha != "" && idioma != "" && paisOrigem != "") {
                if (senha == confirmeSenha)
                    cadastrarRefugiado(username, nome, senha, idioma, paisOrigem, telefone, email)
                else
                    Toast.makeText(this, "As senhas diferem", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this, "Preencha todos os campos antes de continuar", Toast.LENGTH_LONG).show()
        }
    }

    fun cadastrarRefugiado(username: String, nome: String, senha: String, idioma: String, paisDeOrigem: String, telefone: String, email: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(RefugiadoService::class.java)

        var newRef = Refugiado(username, nome, senha, idioma, paisDeOrigem, telefone, email)

        val callback : Call<Refugiado>? = service.postRefugiado(newRef)

        val gUser = application as GlobalUser
        gUser.setGlobalRefugiado(newRef)

        callback!!.enqueue(object : retrofit2.Callback<Refugiado> {
            override fun onResponse(
                call: Call<Refugiado>?,
                response: Response<Refugiado>?
            ) {
                Toast.makeText(this@CadastroRefugiado, "Cadastro feito com sucesso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@CadastroRefugiado, InicialRefugiado::class.java))
            }
            override fun onFailure(call: Call<Refugiado>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                startActivity(Intent(this@CadastroRefugiado, InicialRefugiado::class.java))
            }
        })
    }
}