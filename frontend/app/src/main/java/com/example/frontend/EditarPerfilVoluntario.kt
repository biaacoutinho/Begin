package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val txtEmail = findViewById<EditText>(R.id.update_email)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        txtIdiomas.setText(user?.idioma)
        txtHabilidade.setText(user?.habilidade)
        txtTelefone.setText(user?.telefone)

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
            salvarAlteracoes()
            startActivity(Intent(this, PerfilVoluntario::class.java))
        }
    }

    fun salvarAlteracoes(){

    }
}