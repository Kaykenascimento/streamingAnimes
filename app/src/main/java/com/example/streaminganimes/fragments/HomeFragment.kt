package com.example.streaminganimes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.AnimeDetalhesActivity
import com.example.streaminganimes.activitys.AssistirEpActivity
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.AnimesDao
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelHistorico
import com.google.type.DateTime
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {
    private val animesPopularesLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesRecentesLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesAventuraLista: ArrayList<ModelAnimes> = ArrayList()
    private val animesAcaoLista: ArrayList<ModelAnimes> = ArrayList()
    private val animeSugerido1Lista: ArrayList<ModelAnimes> = ArrayList()
    private val animeSugerido2Lista: ArrayList<ModelAnimes> = ArrayList()
    private val continueAssistindoLista: ArrayList<ModelHistorico> = ArrayList()
    private val animesDao = AnimesDao()
    private val usuarioDao = UsuarioDao()
    private val auth = ConfFireBase.firebaseAuth!!
    private val user = auth.currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View =  inflater.inflate(R.layout.fragment_home, container, false)

        val rcPopulares = view.findViewById<RecyclerView>(R.id.rcAnimesPopulares)
        val rcRecentes = view.findViewById<RecyclerView>(R.id.rcAnimesRecentes)
        val rcAventura = view.findViewById<RecyclerView>(R.id.rcAnimesAventura)
        val rcAcao = view.findViewById<RecyclerView>(R.id.rcAnimesAcao)
        val rcAnimeSugerido1 = view.findViewById<RecyclerView>(R.id.rcAnimeSugerido1)
        val rcAnimeSugerido2 = view.findViewById<RecyclerView>(R.id.rcAnimeSugerido2)
        val rcContinueAssistindo = view.findViewById<RecyclerView>(R.id.rcContinueAssistindo)
        val tvContinueAssistindo = view.findViewById<TextView>(R.id.tvContinueAssistindo)
        val tvAcao = view.findViewById<TextView>(R.id.tvAcao)
        val tvAventura = view.findViewById<TextView>(R.id.tvAventura)
        val tvAdRecentes = view.findViewById<TextView>(R.id.tvAdRecentes)
        val tvPopulares = view.findViewById<TextView>(R.id.tvPopulares)

        carregarAnimes(rcPopulares,
            rcRecentes,
            rcAventura,
            rcAcao,
            rcAnimeSugerido1,
            rcAnimeSugerido2,
            rcContinueAssistindo,
            tvContinueAssistindo, tvPopulares, tvAdRecentes, tvAventura, tvAcao)

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
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
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
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
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
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
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
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
            startActivity(intent)
        }

        rcContinueAssistindo.affectOnItemClicks { position, view ->
            val posicao = continueAssistindoLista[position]
            val intent = Intent(activity, AssistirEpActivity::class.java)
            intent.putExtra("titulo", posicao.tituloEp)
            intent.putExtra("link", posicao.link)
            intent.putExtra("codigoEp", posicao.codigoEp)
            intent.putExtra("codigoAnime", posicao.codigoAnime)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("duracao", posicao.duracao)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("sinopse", posicao.sinopseEp)
            intent.putExtra("saga", posicao.saga)
            intent.putExtra("minutoAssistido", posicao.minutoAssistido)
            intent.putExtra("tela", HomeFragment().javaClass.simpleName)
            startActivity(intent)
        }
        return view
    }

    private fun carregarAnimes(rcPopulares: RecyclerView,
                               rcRecentes: RecyclerView,
                               rcAventura: RecyclerView,
                               rcAcao: RecyclerView,
                               rcAnimeSugerido1: RecyclerView,
                               rcAnimeSugerido2: RecyclerView,
                               rcContinueAssistindo: RecyclerView,
                               tvContinueAssistindo: TextView, tvPopulares: TextView,
                               tvAdRecentes: TextView,
                               tvAventura: TextView,
                               tvAcao: TextView){
        animesDao.carregarAnimesPopulares(animesPopularesLista, rcPopulares, tvPopulares)
        animesDao.carregarAnimesRecentes(animesRecentesLista, rcRecentes, tvAdRecentes)
        animesDao.carregarAnimesAventura(animesAventuraLista, rcAventura, tvAventura)
        animesDao.carregarAnimesAcao(animesAcaoLista, rcAcao, tvAcao)
        animesDao.carregarAnimeSugerido1(animeSugerido1Lista, rcAnimeSugerido1)
        animesDao.carregarAnimeSugerido2(animeSugerido2Lista, rcAnimeSugerido2)
        if(user != null) {
            usuarioDao.carregarContinueAssistindo(
                continueAssistindoLista,
                rcContinueAssistindo,
                tvContinueAssistindo
            )
        }
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}