package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelGeneros
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_generos.view.*

class AdapterGeneros(private val generosLista: ArrayList<ModelGeneros>) : RecyclerView.Adapter<AdapterGeneros.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeGenero = itemView.tvNomeGenero
        val imagemGenero = itemView.ivGenero
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_generos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = generosLista[position]
        holder.nomeGenero.text = posicao.nomeGenero

        Picasso.get().load(posicao.imagem).into(holder.imagemGenero)
    }

    override fun getItemCount(): Int {
        return generosLista.size
    }
}