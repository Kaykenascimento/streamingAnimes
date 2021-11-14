package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelFavoritos
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_favoritos.view.*
import java.util.zip.Inflater

class AdapterFavoritos(private val listaFavoritos: ArrayList<ModelFavoritos>) : RecyclerView.Adapter<AdapterFavoritos.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.ivAnimeFavLista
        val nomeAnime = itemView.tvNomeAnimeFav
        val dataAd = itemView.tvDataAdicaoFav
        val tipo = itemView.tvTipoFav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_favoritos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = listaFavoritos[position]

        holder.nomeAnime.text = posicao.nomeAnime
        holder.dataAd.text = "Adicionado em: ${posicao.dataAdicionado}"
        holder.tipo.text = posicao.tipo

        Picasso.get().load(posicao.imagem).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return listaFavoritos.size
    }
}