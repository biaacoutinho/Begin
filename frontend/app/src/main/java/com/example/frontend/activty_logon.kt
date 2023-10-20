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
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.VoluntarioService
import retrofit2.Call
import retrofit2.Response
import com.example.frontend.GlobalUser as GlobalUser

class activty_logon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activty_logon)

        val tvLogin = findViewById<TextView>(R.id.signinRedirectText)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)
        val txtUsername = findViewById<EditText>(R.id.logon_username)
        val txtNome = findViewById<EditText>(R.id.logon_name)
        val txtSenha = findViewById<EditText>(R.id.logon_password)
        val txtConfirmSenha = findViewById<EditText>(R.id.logon_confirmePassword)
        val txtCPF = findViewById<EditText>(R.id.logon_cpf)
        val txtTelefone = findViewById<EditText>(R.id.logon_telefone)
        val txtEmail = findViewById<EditText>(R.id.logon_email)
        val txtIdiomas = findViewById<EditText>(R.id.logon_idiomas)
        val txtHabiliades = findViewById<EditText>(R.id.logon_habilidades)


        tvLogin.setOnClickListener({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        })

        btnCadastrar.setOnClickListener({
            var username: String = txtUsername.text.toString()
            var nome: String = txtNome.text.toString()
            var senha: String = txtSenha.text.toString()
            var confirmeSenha: String = txtConfirmSenha.text.toString()
            var cpf: String = txtCPF.text.toString()
            var telefone: String = txtTelefone.text.toString()
            var email: String = txtEmail.text.toString()
            var idiomas: String = txtIdiomas.text.toString()
            var habiliades: String = txtHabiliades.text.toString()

            if (username != "" && nome != "" && senha != "" && confirmeSenha != "" && cpf != "")
            {
                if (senha != confirmeSenha)
                    Toast.makeText(this@activty_logon, "As senhas diferem", Toast.LENGTH_LONG).show()
                else
                    cadastrarVoluntario(username, nome, senha, cpf, telefone, email, idiomas, habiliades)
            }
            else
                Toast.makeText(this@activty_logon, "Preencha todos os campos antes de prosseguir", Toast.LENGTH_LONG).show()
        })
    }

    fun cadastrarVoluntario (username: String, nome: String, senha: String, cpf: String, telefone: String, email: String, idiomas: String, habilidade: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(VoluntarioService::class.java)

        var newVolun = Voluntario(username, nome, senha, idiomas, cpf, telefone, habilidade, email)

        val callback : Call<List<Voluntario>>? = service.postVoluntario(newVolun)

        val gUser = application as GlobalUser
        gUser.setGlobalVoluntario(newVolun)

        callback!!.enqueue(object : retrofit2.Callback<List<Voluntario>> {
            override fun onResponse(
                call: Call<List<Voluntario>>?,
                response: Response<List<Voluntario>>?
            ) {
                Toast.makeText(this@activty_logon, "Cadastro feito com sucesso", Toast.LENGTH_LONG).show()
                val intent = Intent(this@activty_logon, InicialVoluntario::class.java)
                startActivity(intent)
            }
            override fun onFailure(call: Call<List<Voluntario>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                Toast.makeText(this@activty_logon, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }
}