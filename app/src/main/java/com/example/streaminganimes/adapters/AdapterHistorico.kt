package com.example.streaminganimes.adapteOrs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelHistorico
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_historico.view.*

class AdapterHistorico(private val historicoLista: ArrayList<ModelHistorico>) : RecyclerView.Adapter<AdapterHistorico.ViewHolder>()  {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeAnime = itemView.tvNomeAnimeHist
        val titulo = itemView.tvTituloEpHist
        val imagem = itemView.ivHistorico
        val progresso = itemView.dtbHistorico
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_historico, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val posicao = historicoLista[position]

        if(posicao.tipo == "Anime"){
            holder.nomeAnime.text = posicao.nomeAnime
            holder.titulo.text = posicao.tituloEp
        }
        else if(posicao.tipo == "Filme"){
            holder.nomeAnime.text = posicao.nomeAnime
            holder.titulo.text = posicao.tipo
        }

        holder.progresso.isEnabled = false
        holder.progresso.setDuration(posicao.duracao.toDouble().toLong() * 60000)
        holder.progresso.setPosition(posicao.minutoAssistido)

        Picasso.get().load(posicao.imagem).into(holder.imagem)
    }

    override fun getItemCount(): Int {
       return historicoLista.size
    }
}

