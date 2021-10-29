package com.example.streaminganimes.adapters

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.inicioActivity
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelEpisodios
import com.example.streaminganimes.models.ModelHistorico
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_anime_sugerido.view.*

class AdapterAnimeSugerido(private val animeSugeridoLista: ArrayList<ModelAnimes>) : RecyclerView.Adapter<AdapterAnimeSugerido.ViewHolder>() {

    private val episodiosDao = EpisodiosDao()
    private val episodioLista: ArrayList<ModelEpisodios> = ArrayList()

    private fun desembrulhar(context: Context): Activity? {
        var context: Context? = context
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }
        return context as Activity?
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.ivAnimeSugerido
        val nomeAnime: TextView = itemView.tvNomeAnimeSug
        val sinopse: TextView = itemView.tvSinopseAnimeSug
        val botao: Button = itemView.btAssistirAgora
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAnimeSugerido.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_anime_sugerido, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterAnimeSugerido.ViewHolder, position: Int) {
        val posicao = animeSugeridoLista[position]

        holder.nomeAnime.text = posicao.nomeAnime
        holder.sinopse.text = posicao.sinopse
        Picasso.get().load(posicao.imagem).into(holder.imageView)

        holder.botao.setOnClickListener {
            episodiosDao.carregarAssistirAgora(posicao.codigo, posicao.nomeAnime, it.context)
        }
    }

    override fun getItemCount(): Int {
        return animeSugeridoLista.size
    }
}