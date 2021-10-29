package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelAnimes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_animes.view.*

class AdapterAnimes(private val animesLista: ArrayList<ModelAnimes>) : RecyclerView.Adapter<AdapterAnimes.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.imageViewAnime
        val tituloAnime: TextView = itemView.tvNomeAnime
        val tipo: TextView = itemView.tvTipo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_animes, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = animesLista[position]
        Picasso.get().load(posicao.imagem).into(holder.imageView)
        holder.tituloAnime.text = posicao.nomeAnime
        holder.tipo.text = posicao.tipo
    }

    override fun getItemCount(): Int {
        return animesLista.size
    }
}