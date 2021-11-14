package com.example.streaminganimes.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.AnimeDetalhesActivity
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.AnimesDao
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelGeneros
import java.sql.Array

class CatalogoFragment : Fragment() {

    private val animesDao = AnimesDao()
    private val catalogoLista: ArrayList<ModelAnimes> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_catalogo, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcCatalogo)

        carregarCatalogo(catalogoLista, recyclerView)

        recyclerView.affectOnItemClicks { position, view ->
            val posicao = catalogoLista[position]
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

        return view
    }

    private fun carregarCatalogo(catalogoLista: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
        animesDao.carregarCatalogo(catalogoLista, recyclerView)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}