package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.AnimesDao
import com.example.streaminganimes.models.ModelAnimes
import kotlinx.android.synthetic.main.activity_buscar_animes.*

class BuscarAnimesActivity : AppCompatActivity() {

    private val animesDao = AnimesDao()
    private val animesLista: ArrayList<ModelAnimes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_animes)

        svBuscarAnimes.queryHint = "Buscar Animes"
        svBuscarAnimes.isIconified = false
        svBuscarAnimes.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.length >= 3){
                    buscarAnimes(newText!!, animesLista)
                    rcBuscaAnimes.visibility = VISIBLE
                }
                else{
                    animesLista.clear()
                    rcBuscaAnimes.visibility = GONE
                }
                return true
            }
        })

        rcBuscaAnimes.affectOnItemClicks { position, view ->
            val posicao = animesLista[position]
            val intent = Intent(this, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigo)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            intent.putExtra("tela", this?.javaClass!!.simpleName)
            startActivity(intent)
        }
    }

    private fun buscarAnimes(nomeAnime: String, animesLista: ArrayList<ModelAnimes>){
        animesDao.buscarAnimes(nomeAnime, animesLista, rcBuscaAnimes, this)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}