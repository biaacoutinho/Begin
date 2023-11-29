package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.AvaliacaoVoluntario
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.AvaliacaoVoluntarioService
import com.example.frontend.API.services.RefugiadoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilVoluntario: AppCompatActivity() {

    var jaAvaliou: Boolean = false
    var avaliacaoFeita: AvaliacaoVoluntario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_voluntario)

        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)
        val btnSolicitao = findViewById<FloatingActionButton>(R.id.btnNotificacao)
        val btnDeslogin = findViewById<TextView>(R.id.btLogOut)
        val btnEditar = findViewById<FloatingActionButton>(R.id.btnEditarVolun)

        val btnCurtir = findViewById<FloatingActionButton>(R.id.btnCurtir)
        val qtdLikes = findViewById<TextView>(R.id.qtdLikes)
        val btnDescurtir = findViewById<FloatingActionButton>(R.id.btnDescurtir)
        val qtdDislikes = findViewById<TextView>(R.id.qtdDislikes)


        val tvNome = findViewById<TextView>(R.id.tvNomeVol)
        val tvUsername = findViewById<TextView>(R.id.tvUsernameVol)
        val tvIdiomas = findViewById<TextView>(R.id.tvIdiomasVol)
        val tvHabilidade = findViewById<TextView>(R.id.tvHabilidadesVol)
        val tvTelefone = findViewById<TextView>(R.id.tvTelefoneVol)
        val tvEmail = findViewById<TextView>(R.id.tvEmailVol)


        var ondeVeio = intent.getStringExtra("ondeVeio")

        if(ondeVeio == "conexao")
        {
            btnDeslogin.visibility = View.INVISIBLE
            btnDeslogin.isClickable = false
            btnEditar.visibility = View.INVISIBLE
            btnEditar.isClickable = false
            btnSolicitao.visibility = View.INVISIBLE
            btnSolicitao.isClickable = false

            val user: Voluntario = intent.getSerializableExtra("voluntario") as Voluntario

            val gUser = application as GlobalUser
            val refugiado = gUser.getGlobalRefugiado()

            jaAvaliou = false

            tvNome.text = user?.nome
            tvUsername.text = "@" + user?.username
            tvIdiomas.text = user?.idioma
            tvHabilidade.text = user?.habilidade
            tvTelefone.text = user?.telefone

            if (user?.email != null)
                tvEmail.text = user?.email
            else
                tvEmail.text = "Nenhum email foi cadastrado"

            btnVoltar.setOnClickListener(){
                startActivity(Intent(this, InicialVoluntario::class.java))
            }

            btnCurtir.setOnClickListener {
                if (jaAvaliou) {
                    if (avaliacaoFeita?.like != true) {
                        salvarAvaliacao(user.username, refugiado!!.username, btnCurtir, btnDescurtir, qtdLikes, qtdDislikes, true)
                    }
                } else {
                    salvarAvaliacao(user.username, refugiado!!.username, btnCurtir, btnDescurtir, qtdLikes, qtdDislikes, true)
                }
            }


            btnDescurtir.setOnClickListener(){
                if (jaAvaliou) {
                    if (avaliacaoFeita?.dislike != true) {
                        salvarAvaliacao(user.username, refugiado!!.username, btnCurtir, btnDescurtir, qtdLikes, qtdDislikes, false)
                    }
                } else {
                    salvarAvaliacao(user.username, refugiado!!.username, btnCurtir, btnDescurtir, qtdLikes, qtdDislikes, false) 
                }
            }

            getAvaliacaoDoRefugiado(user.username, refugiado!!.username, btnCurtir, btnDescurtir)
            getLikesEDislikes(user.username, qtdLikes, qtdDislikes)

        }
        else{
            btnCurtir.isClickable = false
            btnDescurtir.isClickable = false

            qtdLikes.isClickable = false
            qtdDislikes.isClickable = false

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

            getLikesEDislikes(user!!.username, qtdLikes, qtdDislikes)
        }
    }

    fun getLikesEDislikes(voluntario: String, likes: TextView, dislikes: TextView){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(AvaliacaoVoluntarioService::class.java)
        val callback = service.getAvaliacoes(voluntario)

        var somaLikes = 0
        var somaDislikes = 0

        callback.enqueue(object : retrofit2.Callback<List<AvaliacaoVoluntario>> {
            override fun onResponse(
                call: Call<List<AvaliacaoVoluntario>>?,
                response: Response<List<AvaliacaoVoluntario>>?
            ) {
                val avaliacaoList = response?.body()
                if (avaliacaoList != null) {
                    if (response!!.isSuccessful) {
                        for (avaliacao in avaliacaoList) {
                            if (avaliacao.like)
                                somaLikes += 1
                            else if(avaliacao.dislike)
                                somaDislikes += 1
                        }

                        likes.text = somaLikes.toString()
                        dislikes.text = somaDislikes.toString()

                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<AvaliacaoVoluntario>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getAvaliacaoDoRefugiado(voluntario: String, refugiado: String, btnCurtir: FloatingActionButton, btnDescurtir: FloatingActionButton){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(AvaliacaoVoluntarioService::class.java)
        val callback = service.getAvaliacaoPorRefugiado(refugiado, voluntario)
        callback.enqueue(object : retrofit2.Callback<List<AvaliacaoVoluntario>> {
            override fun onResponse(
                call: Call<List<AvaliacaoVoluntario>>?,
                response: Response<List<AvaliacaoVoluntario>>?
            ) {
                val avaliacaoList = response?.body()
                if (!avaliacaoList.isNullOrEmpty()) {
                    if (response!!.isSuccessful) {
                        jaAvaliou = true
                        avaliacaoFeita = avaliacaoList[0]
                        if (avaliacaoList[0].like)
                            btnCurtir.setImageResource(R.drawable.curtido_icon)
                        else if(avaliacaoList[0].dislike)
                            btnDescurtir.setImageResource(R.drawable.descutido_icon)
                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<AvaliacaoVoluntario>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun salvarAvaliacao(
        voluntario: String,
        refugiado: String,
        btnCurtir: FloatingActionButton,
        btnDescurtir: FloatingActionButton,
        likes: TextView,
        dislikes: TextView,
        curtiu: Boolean
    ) {
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(AvaliacaoVoluntarioService::class.java)

        var like = true
        var dislike = true
        if(curtiu)
            dislike = false
        else
            like = false

        val avaliacao = AvaliacaoVoluntario(0, refugiado, voluntario, like, dislike)

        var callback: Call<List<AvaliacaoVoluntario>>

        if (jaAvaliou)
            callback = service.putAvaliacao(avaliacaoFeita!!.id, avaliacao)
        else
            callback = service.postAvaliacao(avaliacao)

        callback.enqueue(object : retrofit2.Callback<List<AvaliacaoVoluntario>> {
            override fun onResponse(
                call: Call<List<AvaliacaoVoluntario>>?,
                response: Response<List<AvaliacaoVoluntario>>?
            ) {
                val avaliacaoList = response?.body()
                if (!avaliacaoList.isNullOrEmpty()) {
                    if (response.isSuccessful) {
                        avaliacaoFeita = avaliacaoList[0]
                        val qtdLikes = likes.text.toString().toInt()
                        val qtdDislikes = dislikes.text.toString().toInt()

                        if (curtiu)
                        {
                            btnCurtir.setImageResource(R.drawable.curtido_icon)
                            btnDescurtir.setImageResource(R.drawable.icon_deslike)

                            if (jaAvaliou)
                                dislikes.text = (qtdDislikes - 1).toString()

                            likes.text = (qtdLikes + 1).toString()
                            jaAvaliou = true
                        }
                        else
                        {
                            btnCurtir.setImageResource(R.drawable.icon_curtir)
                            btnDescurtir.setImageResource(R.drawable.descutido_icon)

                            if (jaAvaliou)
                                likes.text = (qtdLikes - 1).toString()

                            dislikes.text = (qtdDislikes + 1).toString()
                            jaAvaliou = true
                        }
                        Toast.makeText(this@PerfilVoluntario, "Avaliação feita com sucesso", Toast.LENGTH_LONG).show()
                    } else {
                        val errorMessage = response?.errorBody()?.string()
                    }
                }
            }

            override fun onFailure(call: Call<List<AvaliacaoVoluntario>>, t: Throwable) {
                // Lidar com o caso de falha
            }
        })
    }
}