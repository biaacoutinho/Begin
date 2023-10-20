package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.API.models.Voluntario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PerfilVoluntario: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_voluntario)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)
        val btnSolicitao = findViewById<FloatingActionButton>(R.id.btnNotificacao)
        val btnDeslogin = findViewById<TextView>(R.id.btLogOut)
        val btnEditar = findViewById<FloatingActionButton>(R.id.btnEditarVolun)

        btnVoltar.setOnClickListener(){
            startActivity(Intent(this, InicialVoluntario::class.java))
        }

        btnSolicitao.setOnClickListener(){
            startActivity(Intent(this, SolicitacaoRefugiado::class.java))
        }

        btnDeslogin.setOnClickListener(){
            val builder = AlertDialog.Builder(this@PerfilVoluntario)
            builder.setMessage("Certeza que deseja sair?")
                .setCancelable(false)
                .setPositiveButton("Sim") { dialog, id ->
                    val alert = builder.create()
                    alert.show()
                    val gUser = application as GlobalUser
                    gUser.setGlobalVoluntario(null)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Não") { dialog, id ->
                    dialog.dismiss()
                }

        }

        val tvNome = findViewById<TextView>(R.id.tvNomeVol)
        val tvUsername = findViewById<TextView>(R.id.tvUsernameVol)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomasVol)
        val tvHabilidade = findViewById<TextView>(R.id.tvHabilidadesVol)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefoneVol)
        val tvEmail = findViewById<TextView>(R.id.tvEmailVol)

        val gUser = application as GlobalUser
        val user = gUser.getGlobalVoluntario()

        tvNome.text = user?.nome
        tvUsername.text = "@" + user?.username
        tvIdiomas.text = user?.idioma
        tvHabilidade.text = user?.habilidade
        tvTelefone.text = user?.telefone

        if (user?.email != null)
            tvEmail.text = user?.email
        else
            tvEmail.text = "Nenhum email foi cadastrado"

        btnEditar.setOnClickListener(){
            startActivity(Intent(this, EditarPerfilVoluntario::class.java))
        }
    }
}