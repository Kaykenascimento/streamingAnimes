package com.example.streaminganimes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.models.ModelHistorico
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_anime_sugerido.view.*
import kotlinx.android.synthetic.main.adapter_continue_assistindo.view.*
import kotlin.math.roundToLong

class AdapterContinueAssistindo(private val episodioLista: ArrayList<ModelHistorico>) : RecyclerView.Adapter<AdapterContinueAssistindo.ViewHolder>() {

    private val episodiosDao = EpisodiosDao()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeAnime = itemView.tvNomeAnimeCont
        val titulo = itemView.tvTituloEpCont
        val imagem = itemView.ivContinueAssistindo
        val progresso = itemView.pgMinuto
        val botao = itemView.btAssistirAgora
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterContinueAssistindo.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_continue_assistindo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterContinueAssistindo.ViewHolder, position: Int) {
        val posicao = episodioLista[position]

        if(posicao.tipo == "Anime"){
            holder.nomeAnime.text = posicao.nomeAnime
            holder.titulo.text = "${posicao.tituloEp} - ${posicao.tipo}"
        }
        else if(posicao.tipo == "Filme"){
            holder.nomeAnime.text = posicao.nomeAnime
            holder.titulo.text = posicao.tipo
        }

        val duration = posicao.duracao.toDouble()
        val dur = (duration * 60000).toLong()
        holder.progresso.isEnabled = false
        holder.progresso.setDuration(dur)
        holder.progresso.setPosition(posicao.minutoAssistido)

        Picasso.get().load(posicao.imagem).into(holder.imagem)
    }

    override fun getItemCount(): Int {
        return episodioLista.size
    }
}