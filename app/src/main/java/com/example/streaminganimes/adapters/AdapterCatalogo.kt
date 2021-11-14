package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelAnimes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_catalogo.view.*

class AdapterCatalogo(private val catalogoLista: ArrayList<ModelAnimes>) : RecyclerView.Adapter<AdapterCatalogo.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagem = itemView.ivCatalogo
        val nomeAnime = itemView.tvNomeAnimeCat
        val tipo = itemView.tvTipoCat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_catalogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = catalogoLista[position]
        holder.nomeAnime.text = posicao.nomeAnime
        holder.tipo.text = posicao.tipo

        Picasso.get().load(posicao.imagem).into(holder.imagem)
    }

    override fun getItemCount(): Int {
        return catalogoLista.size
    }
}