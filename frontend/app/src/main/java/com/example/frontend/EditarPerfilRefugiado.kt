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
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.RefugiadoService
import com.example.frontend.API.services.VoluntarioService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response
import java.sql.Ref

class EditarPerfilRefugiado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_refugiado)

        val btnSalvarAlteracao = findViewById<FloatingActionButton>(R.id.btnSalvarAlteracao)
        val btnSair = findViewById<FloatingActionButton>(R.id.btnVoltar)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val txtIdiomas = findViewById<EditText>(R.id.update_idioma)
        val txtPais = findViewById<EditText>(R.id.update_pais)
        val txtTelefone = findViewById<EditText>(R.id.update_telefone)
        val txtEmail = findViewById<EditText>(R.id.update_email)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalRefugiado()
        

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        txtIdiomas.setText(user?.idioma)
        txtPais.setText(user?.paisOrigem)
        txtTelefone.setText(user?.telefone)

        if (user?.email != null)
            txtEmail.setText(user?.email)
        else
            txtEmail.setText("Nenhum email foi cadastrado")

        btnSair.setOnClickListener(){
            val builder = AlertDialog.Builder(this@EditarPerfilRefugiado)
            builder.setMessage("Tem certeza que deseja voltar? \nLembre-se que as alterações nao serão salvas!")
                .setCancelable(false)
                .setPositiveButton("Sim") { dialog, id ->
                    startActivity(Intent(this, perfilRefugiado::class.java))
                }
                .setNegativeButton("Não") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }


        btnSalvarAlteracao.setOnClickListener() {
            salvarAlteracoes(user, txtIdiomas.text.toString(), txtPais.text.toString(), txtTelefone.text.toString(), txtEmail.text.toString())
        }
    }

    fun salvarAlteracoes(user: Refugiado?, idioma: String, pais: String, telefone: String, email: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(RefugiadoService::class.java)

        var newRef = Refugiado(user!!.username, user.nome, user.senha, idioma, telefone, pais, email)

        val callback : Call<List<Refugiado>>? = service.putRefugido(user.username, newRef)

        val gUser = application as GlobalUser
        gUser.setGlobalRefugiado(newRef)

        callback!!.enqueue(object : retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(
                call: Call<List<Refugiado>>,
                response: Response<List<Refugiado>>?
            ) {
                Toast.makeText(this@EditarPerfilRefugiado, "Edições feitas com sucesso", Toast.LENGTH_LONG).show()
                val intent = Intent(this@EditarPerfilRefugiado, perfilRefugiado::class.java)
                startActivity(intent)
            }
            override fun onFailure(call: Call<List<Refugiado>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                Toast.makeText(this@EditarPerfilRefugiado, "Ocorreu um erro ao salvar as edições", Toast.LENGTH_LONG).show()
            }
        })
    }
}