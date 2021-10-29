package com.example.streaminganimes.fragments

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.AnimeDetalhesActivity
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.AnimesDao
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelHistorico
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList


class HomeFragment : Fragment() {
    private val animesPopularesLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesRecentesLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesAventuraLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesAcaoLista: ArrayList<ModelAnimes> = ArrayList()
    private val animeSugerido1Lista: ArrayList<ModelAnimes> = ArrayList()
    private val animeSugerido2Lista: ArrayList<ModelAnimes> = ArrayList()
    private val continueAssistindoLista: ArrayList<ModelHistorico> = ArrayList()
    private val dao = AnimesDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View =  inflater.inflate(R.layout.fragment_home, container, false)

        val rcPopulares = view.findViewById<RecyclerView>(R.id.rcAnimesPopulares)
        val rcRecentes = view.findViewById<RecyclerView>(R.id.rcAnimesRecentes)
        val rcAventura = view.findViewById<RecyclerView>(R.id.rcAnimesAventura)
        val rcAcao = view.findViewById<RecyclerView>(R.id.rcAnimesAcao)
        val rcAnimeSugerido1 = view.findViewById<RecyclerView>(R.id.rcAnimeSugerido1)
        val rcAnimeSugerido2 = view.findViewById<RecyclerView>(R.id.rcAnimeSugerido2)

        carregarAnimes(rcPopulares, rcRecentes, rcAventura, rcAcao, rcAnimeSugerido1, rcAnimeSugerido2)

        rcPopulares.affectOnItemClicks { position, view ->
            val posicao = animesPopularesLista[position]
            val intent = Intent(activity, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigo)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            startActivity(intent)
        }

        rcRecentes.affectOnItemClicks { position, view ->
            val posicao = animesRecentesLista[position]
            val intent = Intent(activity, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigo)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            startActivity(intent)
        }

        rcAventura.affectOnItemClicks { position, view ->
            val posicao = animesAventuraLista[position]
            val intent = Intent(activity, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigo)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            startActivity(intent)
        }

        rcAcao.affectOnItemClicks { position, view ->
            val posicao = animesAcaoLista[position]
            val intent = Intent(activity, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigo)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            startActivity(intent)
        }
        return view
    }

    private fun carregarAnimes(rcPopulares: RecyclerView, rcRecentes: RecyclerView, rcAventura: RecyclerView, rcAcao: RecyclerView, rcAnimeSugerido1: RecyclerView, rcAnimeSugerido2: RecyclerView){
        dao.carregarAnimesPopulares(animesPopularesLista, rcPopulares )
        dao.carregarAnimesRecentes(animesRecentesLista, rcRecentes)
        dao.carregarAnimesAventura(animesAventuraLista, rcAventura)
        dao.carregarAnimesAcao(animesAcaoLista, rcAcao)
        dao.carregarAnimeSugerido1(animeSugerido1Lista, rcAnimeSugerido1)
        dao.carregarAnimeSugerido2(animeSugerido2Lista, rcAnimeSugerido2)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}