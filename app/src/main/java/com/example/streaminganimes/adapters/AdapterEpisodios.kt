package com.example.streaminganimes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.models.ModelEpisodios
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_episodios.view.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class AdapterEpisodios(private val episodiosLista: ArrayList<ModelEpisodios>, private val tipo: String) : RecyclerView.Adapter<AdapterEpisodios.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.ivEpisodio
        val tituloEp: TextView = itemView.tvTituloEp
        val ano: TextView = itemView.tvAnoEp
        val duracao: TextView = itemView.tvDuracaoEp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterEpisodios.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_episodios, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterEpisodios.ViewHolder, position: Int) {
        val posicao = episodiosLista[position]

        holder.tituloEp.text = posicao.titulo
        holder.ano.text = "Ano: ${posicao.ano}"
        holder.duracao.text = "${posicao.duracao}min"

        Picasso.get().load(posicao.imagem).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return episodiosLista.size
    }


}