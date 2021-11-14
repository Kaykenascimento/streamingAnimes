package com.example.streaminganimes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelAnimes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_busca_animes.view.*
import kotlin.collections.ArrayList

class AdapterBuscaAnimes(private val buscaAnimes: ArrayList<ModelAnimes>,
                         private val context: Context) : RecyclerView.Adapter<AdapterBuscaAnimes.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeAnime = itemView.tvNomeAnimeBusca
        val generos = itemView.chipGroupGeneros
        val qtEps = itemView.tvQtEpsBusca
        val imagem = itemView.ivAnimeBusca
        val tipo = itemView.tvTipoBusca
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_busca_animes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = buscaAnimes[position]

        holder.nomeAnime.text = posicao.nomeAnime
        holder.qtEps.text = "Quantidade de episódios: ${posicao.qtEpisodios.toString()}"

        if(posicao.tipo == "Filme"){holder.qtEps.visibility = GONE}
        holder.tipo.text = posicao.tipo

        separarEAdicionarGenero(posicao, holder)
        Picasso.get().load(posicao.imagem).into(holder.imagem)
    }

    private fun separarEAdicionarGenero(posicao: ModelAnimes, holder: ViewHolder) {
        val genero = posicao.genero
        val separado = genero.split("/").toTypedArray()
        for (i in separado.indices) {
            adicionarChip(separado[i], holder.generos)
        }
    }

    override fun getItemCount(): Int {
        return buscaAnimes.size
    }

    private fun adicionarChip(generos: String, chipGroup: ChipGroup){
        val chip = Chip(context)
        chip.text = generos
        chip.isClickable = false
        chipGroup.addView(chip)
    }
}