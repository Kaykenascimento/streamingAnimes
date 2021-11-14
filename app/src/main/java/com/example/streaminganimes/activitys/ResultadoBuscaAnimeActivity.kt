package com.example.streaminganimes.activitys

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.AnimesDao
import com.example.streaminganimes.fragments.generos.GenerosListaFragment
import com.example.streaminganimes.models.ModelAnimes
import kotlinx.android.synthetic.main.activity_resultado_busca_animes.*

class ResultadoBuscaAnimeActivity : AppCompatActivity() {

    private val animesDao = AnimesDao()
    private val animesLista: ArrayList<ModelAnimes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_busca_animes)

        val bundle = intent.extras
        val genero = bundle?.getString("genero")
        val tela = bundle?.getString("tela")

        setSupportActionBar(toolbarAnimesGen)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = "Resultados da busca por: $genero"

        carregarAnimesPorGenero(animesLista, genero!!, rcResultadoBusca)

        rcResultadoBusca.affectOnItemClicks { position, view ->
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
    fun carregarAnimesPorGenero(animesLista: ArrayList<ModelAnimes>, genero: String, recyclerView: RecyclerView){
        animesDao.buscarAnimesPorGenero(animesLista, genero, recyclerView, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}