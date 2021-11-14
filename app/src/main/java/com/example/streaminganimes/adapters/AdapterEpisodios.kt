package com.example.streaminganimes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelEpisodios
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_episodios.view.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class AdapterEpisodios(private val episodiosLista: ArrayList<ModelEpisodios>,
                       private val tipo: String,
                       private val context: Context) : RecyclerView.Adapter<AdapterEpisodios.ViewHolder>() {

    private val auth = ConfFireBase.firebaseAuth!!

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.ivEpisodio
        val tituloEp: TextView = itemView.tvTituloEp
        val duracao: TextView = itemView.tvDuracaoEp
        val ano: TextView = itemView.tvAnoEp
        val downloadEp: ImageView = itemView.btBaixarEp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterEpisodios.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_episodios, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterEpisodios.ViewHolder, position: Int) {
        val posicao = episodiosLista[position]

        holder.tituloEp.text = posicao.titulo
        holder.ano.text = posicao.ano

        val user = auth.currentUser
        if(user == null){
            holder.downloadEp.visibility = GONE
        }

        if(tipo == "Filme"){
            val duracao = posicao.duracao.toDouble()
            val duracaoMs = duracao * 60000
            val hora = (duracaoMs / 3600000) % 24
            //val minuto = (duracaoMs / 60000) % 60
            holder.duracao.text = "${hora}h"
        }
        else if (tipo == "Anime"){
            holder.duracao.text = "${posicao.duracao}min"
        }

        holder.downloadEp.setOnClickListener {
            Toast.makeText(context, "Função Indisponível", Toast.LENGTH_SHORT).show()
        }

        Picasso.get().load(posicao.imagem).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return episodiosLista.size
    }
}