package com.example.frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class OngsAdapter(var mList: List<OngData>) : RecyclerView.Adapter<OngsAdapter.OngsViewHolder>() {

    inner class OngsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.title)
        val logo : ImageView = itemView.findViewById(R.id.logo)
    }

    fun setFilteredList(mList: List<OngData>)
    {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_ongs, parent, false)
        return OngsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OngsViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.logo.setImageResource(mList[position].logo)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}