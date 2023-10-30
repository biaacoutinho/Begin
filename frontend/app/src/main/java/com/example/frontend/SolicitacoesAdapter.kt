package com.example.frontend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.API.RetrofitClient
import com.example.frontend.API.models.Conexao
import com.example.frontend.API.models.Voluntario
import com.example.frontend.API.services.ConexaoService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response

class SolicitacoesAdapter(var mList: MutableList<SolicitacoesData>, var user: Voluntario) : RecyclerView.Adapter<SolicitacoesAdapter.SolicitacoesViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun aceitarConexao(refugiado: String){
        val retrofitClient = RetrofitClient.getRetrofit()
        val service = retrofitClient.create(ConexaoService::class.java)
        Log.d("aaa", "b1")
        val newConexao = Conexao(0, refugiado, user.username, false)
        val callback : Call<List<Conexao>> = service.putConexao(refugiado, user.username, newConexao)
        Log.d("aaa", "a1")
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
}