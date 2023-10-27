package com.example.frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SolicitacoesAdapter(var mList: List<SolicitacoesData>) : RecyclerView.Adapter<SolicitacoesAdapter.SolicitacoesViewHolder>() {

    inner class SolicitacoesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeRefugiado : TextView = itemView.findViewById(R.id.nomeRefugiado)
        val userRefugiado : TextView = itemView.findViewById(R.id.userRefugiado)
        val idiomaRefugiado : TextView = itemView.findViewById(R.id.idiomaRefugiado)
        val paisRefugiado : TextView = itemView.findViewById(R.id.paisRefugiado)
    }

    fun setFilteredList(mList: List<SolicitacoesData>)
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
        holder.userRefugiado.text = mList[position].userRefugiado
        holder.idiomaRefugiado.text = mList[position].idiomaRefugiado
        holder.paisRefugiado.text = mList[position].paisRefugiado

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}