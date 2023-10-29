package com.example.frontend

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.BoringLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.services.ConexaoService
import com.example.frontend.API.services.ProfilePictureService
import com.example.frontend.API.services.RefugiadoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ConexaoAdapter(var mList: List<ConexaoData>, private val context: Context, private val application: Application) : RecyclerView.Adapter<ConexaoAdapter.ConexaoViewHolder>() {

    inner class ConexaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgPerfil : ImageView = itemView.findViewById<ShapeableImageView>(R.id.imgPerfil)
        val nomeUsuario : TextView = itemView.findViewById(R.id.nomeUsuario)
        val user : TextView = itemView.findViewById(R.id.user)
        val idiomaUser : TextView = itemView.findViewById(R.id.idioma)
        val habilidadesUser : TextView = itemView.findViewById(R.id.habilidadeUser)
        val btnConectar : FloatingActionButton = itemView.findViewById<FloatingActionButton>(R.id.btnConectar)
        var conectado: Boolean = false

    }

    fun setFilteredList(mList: List<ConexaoData>)
    {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConexaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_conexao_voluntario, parent, false)
        return ConexaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConexaoViewHolder, position: Int) {
        holder.nomeUsuario.text = mList[position].nomeUsuario
        holder.user.text = "@" + mList[position].user
        holder.idiomaUser.text = mList[position].idiomaUser
        holder.habilidadesUser.text = mList[position].habilidadesUser
        getProfileImage(mList[position].user, holder.imgPerfil)


        holder.btnConectar.setImageResource(R.drawable.icon_add)
        verificarBotao(mList[position].user, holder.btnConectar, holder)

        holder.btnConectar.setOnClickListener{
            if (holder.conectado)
                deletarConexao(mList[position].user, holder.btnConectar, holder)
            else
                salvarConexao(mList[position].user, holder.btnConectar, holder)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getProfileImage(user: String, imgPerfil: ImageView){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ProfilePictureService::class.java)
        val callback: Call<ResponseBody> = service.getPicture(user)

        callback.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.isSuccessful == true) {
                    val responseBody = response.body()?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val fileUrl = jsonResponse.optString("url")
                    Picasso.get().load(fileUrl).into(imgPerfil)
                    //val drawable = ContextCompat.getDrawable(context, R.drawable.name_icon)
                    //imgPerfil.setImageDrawable(drawable)
                    //Log.d("atualizou", "atualzou")
                } else {
                    // Lidar com a resposta sem sucesso aqui
                    val errorBody = response?.errorBody()?.string()
                    Log.d("erro", "Erro na resposta: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
            }
        })
    }

    fun salvarConexao(voluntario: String, btnConectar: FloatingActionButton, holder: ConexaoViewHolder){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)

        val gUser = application as GlobalUser
        val refugiado = gUser.getGlobalRefugiado()

        var newConexao = Conexao(0, refugiado!!.username, voluntario, true)
        Log.d("refugiado", newConexao.usernameRefugiado)
        Log.d("voluntario", newConexao.usernameVoluntario)

        val callback : Call<List<Conexao>> = service.postConexao(newConexao)

        callback!!.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                Log.d("foii", "criou a conecao")
                Toast.makeText(context, "Solicitação enviada para @" + voluntario, Toast.LENGTH_LONG).show()
                btnConectar.setImageResource(R.drawable.icon_sino)
                holder.conectado = true
                //startActivity(Intent(this@CadastroRefugiado, InicialRefugiado::class.java))
            }
            override fun onFailure(call: Call<List<Conexao>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                //Toast.makeText(this@CadastroRefugiado, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun verificarBotao(voluntario: String, btnConectar: FloatingActionButton, holder: ConexaoViewHolder){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)

        val gUser = application as GlobalUser
        val refugiado = gUser.getGlobalRefugiado()

        val callback : Call<List<Conexao>> = service.getConexao("refugiado", refugiado!!.username)

        callback!!.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                if (response!!.isSuccessful) {
                    val conexoesList = response.body()

                    if (conexoesList != null){
                        for (conexao in conexoesList){
                            if (conexao.pendente && conexao.usernameVoluntario == voluntario)
                            {
                                btnConectar.setImageResource(R.drawable.icon_sino)
                                holder.conectado = true
                            }
                            else if (!conexao.pendente && conexao.usernameVoluntario == voluntario)
                            {
                                btnConectar.setImageResource(R.drawable.aceitar_icon)
                                holder.conectado = true
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Conexao>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                //Toast.makeText(this@CadastroRefugiado, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun deletarConexao(voluntario: String, btnConectar: FloatingActionButton, holder: ConexaoViewHolder){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)

        val gUser = application as GlobalUser
        val refugiado = gUser.getGlobalRefugiado()

        val callback : Call<List<Conexao>> = service.deleteConexao(refugiado!!.username, voluntario)

        callback!!.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                if (response!!.isSuccessful) {
                    val resposta = response.body()
                    Log.d("foi?", resposta.toString())
                    btnConectar.setImageResource(R.drawable.icon_add)
                    holder.conectado = false
                }
            }
            override fun onFailure(call: Call<List<Conexao>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                //Toast.makeText(this@CadastroRefugiado, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }

    /*fun isURLValid(url: String): Boolean {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .head() // Use uma solicitação HEAD para verificar a existência da URL
            .build()

        try {
            val response: okhttp3.Response = client.newCall(request).execute()
            Log.d("responsta", response.code.toString())
            return response.code == 200 // 200 indica uma resposta bem-sucedida
        } catch (e: Exception) {
            return false // Lidar com exceções, como falta de conectividade ou erros de rede
        }
    }*/
}