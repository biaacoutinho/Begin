package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.VoluntarioService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class EditarPerfilVoluntario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_voluntario)

        val btnSalvarAlteracao = findViewById<FloatingActionButton>(R.id.btnSalvarAlteracao)
        val btnSair = findViewById<FloatingActionButton>(R.id.btnVoltar)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val txtIdiomas = findViewById<EditText>(R.id.update_idioma)
        val txtHabilidade = findViewById<EditText>(R.id.update_habilidade)
        val txtTelefone = findViewById<EditText>(R.id.update_telefone)
        val txtCPF = findViewById<EditText>(R.id.update_cpf)
        val txtEmail = findViewById<EditText>(R.id.update_email)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        txtIdiomas.setText(user?.idioma)
        txtHabilidade.setText(user?.habilidade)
        txtTelefone.setText(user?.telefone)
        txtCPF.setText(user?.cpf)

        if (user?.email != null)
            txtEmail.setText(user?.email)
        else
            txtEmail.setText("Nenhum email foi cadastrado")

        btnSair.setOnClickListener(){
            val builder = AlertDialog.Builder(this@EditarPerfilVoluntario)
            builder.setMessage("Tem certeza que deseja voltar? \nLembre-se que as alterações nao serão salvas!")
                .setCancelable(false)
                .setPositiveButton("Sim") { dialog, id ->
                    startActivity(Intent(this, PerfilVoluntario::class.java))
                }
                .setNegativeButton("Não") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }


        btnSalvarAlteracao.setOnClickListener() {
            salvarAlteracoes(user, txtCPF.text.toString(), txtIdiomas.text.toString(), txtHabilidade.text.toString(), txtTelefone.text.toString(), txtEmail.text.toString())
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }
    }

    fun salvarAlteracoes(user: Voluntario?, cpf: String, idioma: String, habilidade: String, telefone: String, email: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(VoluntarioService::class.java)

        var newVolun = Voluntario(user!!.username, user.nome, user.senha, idioma, cpf, telefone, habilidade, email)

        val callback : Call<List<Voluntario>>? = service.putVoluntario(user.username, newVolun)

        val gUser = application as GlobalUser
        gUser.setGlobalVoluntario(newVolun)

        callback!!.enqueue(object : retrofit2.Callback<List<Voluntario>> {
            override fun onResponse(
                call: Call<List<Voluntario>>,
                response: Response<List<Voluntario>>?
            ) {
                println(response?.body().toString())
                Toast.makeText(this@EditarPerfilVoluntario, "Edições feitas com sucesso", Toast.LENGTH_LONG).show()
                val intent = Intent(this@EditarPerfilVoluntario, InicialVoluntario::class.java)
                startActivity(intent)
            }
            override fun onFailure(call: Call<List<Voluntario>>?, t: Throwable?) {
                println(call.toString())
                val messageProblem: String = t?.message.toString()
                Toast.makeText(this@EditarPerfilVoluntario, messageProblem, Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        })
    }
}