package com.example.frontend

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Refugiado
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.ConexaoService
import com.example.frontend.API.services.RefugiadoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class SolicitacoesAdapter(var mList: MutableList<SolicitacoesData>, var user: Voluntario, var context: Context) : RecyclerView.Adapter<SolicitacoesAdapter.SolicitacoesViewHolder>() {

    inner class SolicitacoesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeRefugiado : TextView = itemView.findViewById(R.id.nomeRefugiado)
        val userRefugiado : TextView = itemView.findViewById(R.id.userRefugiado)
        val idiomaRefugiado : TextView = itemView.findViewById(R.id.idiomaRefugiado)
        val paisRefugiado : TextView = itemView.findViewById(R.id.paisRefugiado)
        val btnAceitar : FloatingActionButton = itemView.findViewById<FloatingActionButton>(R.id.btnAceitar)
        val btnRecusar : FloatingActionButton = itemView.findViewById<FloatingActionButton>(R.id.btnRecusar)
    }

    fun setFilteredList(mList: MutableList<SolicitacoesData>)
    {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitacoesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_solicitacoes_refugiado, parent, false)
        return SolicitacoesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolicitacoesViewHolder, position: Int) {
        holder.nomeRefugiado.text = mList[position].nomeRefugiado
        holder.userRefugiado.text = "@" + mList[position].userRefugiado
        holder.idiomaRefugiado.text = mList[position].idiomaRefugiado
        holder.paisRefugiado.text = mList[position].paisRefugiado

        holder.btnAceitar.setOnClickListener{
            aceitarConexao(mList[position].userRefugiado)

            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mList.removeAt(position)
                notifyItemRemoved(position)

            }
        }

        holder.btnRecusar.setOnClickListener{
            deletarConexao(mList[position].userRefugiado)

            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mList.removeAt(position)
                notifyItemRemoved(position)

            }
        }

        holder.itemView.setOnClickListener {
            val userRef = mList[position].userRefugiado
            val intent = Intent(context, perfilRefugiado::class.java)
            var refugiado = Refugiado("", "", "", "", "", "", "")
            intent.putExtra("ondeVeio", "solicitacao")
            getRefugiado(userRef, refugiado, intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun aceitarConexao(refugiado: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)
        val newConexao = Conexao(0, refugiado, user.username, false)
        val callback : Call<List<Conexao>> = service.putConexao(refugiado, user.username, newConexao)
        callback!!.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                if (response!!.isSuccessful) {
                    val resposta = response.body()
                    Log.d("foi alteracao?", resposta.toString())
                }
            }
            override fun onFailure(call: Call<List<Conexao>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                //Toast.makeText(this@CadastroRefugiado, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun deletarConexao(refugiado: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)

        val callback : Call<List<Conexao>> = service.deleteConexao(refugiado, user.username)

        callback!!.enqueue(object : retrofit2.Callback<List<Conexao>> {
            override fun onResponse(
                call: Call<List<Conexao>>?,
                response: Response<List<Conexao>>?
            ) {
                if (response!!.isSuccessful) {
                    val resposta = response.body()
                    Log.d("foi?", resposta.toString())
                }
            }
            override fun onFailure(call: Call<List<Conexao>>?, t: Throwable?) {
                val messageProblem: String = t?.message.toString()
                Log.d("erro", messageProblem)
                //Toast.makeText(this@CadastroRefugiado, "Não foi possível cadastrar", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getRefugiado(ref : String, refugiado: Refugiado, intent: Intent){
        val retrofitClient = RetrofitClient.getRetrofit()
        val refugiadoService = retrofitClient.create(RefugiadoService::class.java)
        val refCallback = refugiadoService.getRefugiado(ref)

        refCallback.enqueue(object : retrofit2.Callback<List<Refugiado>> {
            override fun onResponse(
                call: Call<List<Refugiado>>?,
                response: Response<List<Refugiado>>?
            ) {
                val refugiadoList = response?.body()
                if (refugiadoList != null) {
                    if (response!!.isSuccessful) {
                        refugiado.nome = refugiadoList[0].nome
                        refugiado.username = refugiadoList[0].username
                        refugiado.senha = refugiadoList[0].senha
                        refugiado.idioma = refugiadoList[0].idioma
                        refugiado.paisOrigem = refugiadoList[0].paisOrigem
                        refugiado.telefone = refugiadoList[0].telefone
                        refugiado.email = refugiadoList[0].email
                        intent.putExtra("refugiado", refugiado)
                        context.startActivity(intent)
                    } else {
                        val errorMessage = response?.errorBody().toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<Refugiado>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}