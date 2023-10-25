package com.example.frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConexaoAdapter(var mList: List<ConexaoData>) : RecyclerView.Adapter<ConexaoAdapter.ConexaoViewHolder>() {

    inner class ConexaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeUsuario : TextView = itemView.findViewById(R.id.nomeUsuario)
        val user : TextView = itemView.findViewById(R.id.user)
        val idiomaUser : TextView = itemView.findViewById(R.id.idioma)
        val habilidadesUser : TextView = itemView.findViewById(R.id.habilidadeUser)
    }

    fun setFilteredList(mList: List<ConexaoData>)
    {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConexaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_outro_teste, parent, false)
        return ConexaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConexaoViewHolder, position: Int) {
        holder.nomeUsuario.text = mList[position].user
        holder.user.text = mList[position].user
        holder.idiomaUser.text = mList[position].idiomaUser
        holder.habilidadesUser.text = mList[position].habilidadesUser
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}