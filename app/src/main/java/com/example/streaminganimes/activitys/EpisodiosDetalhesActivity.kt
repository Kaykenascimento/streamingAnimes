package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.INVISIBLE
import com.example.streaminganimes.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episodios_detalhes.*
import kotlinx.android.synthetic.main.adapter_episodios.*

class EpisodiosDetalhesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodios_detalhes)

        val bundle: Bundle ?= this.intent.extras
        val nomeAnime = bundle?.getString("nomeAnime")
        val titulo = bundle?.getString("titulo")
        val sinopse = bundle?.getString("sinopse")
        val duracao = bundle?.getString("duracao")
        val tipo = bundle?.getString("tipo")
        val imagem = bundle?.getString("imagem")
        val imagemAnime = bundle?.getString("imagemAnime")
        val link = bundle?.getString("link")
        val codigoAnime = bundle?.getString("codigoAnime")
        val codigoEp = bundle?.getString("codigoEp")
        val saga = bundle?.getString("saga")

        carregarItens(nomeAnime, titulo, sinopse, duracao, tipo, imagem)

        fbIniciarEpisodio.setOnClickListener {
            val intent = Intent(this, AssistirEpActivity::class.java)
            intent.putExtra("titulo", titulo)
            intent.putExtra("link", link)
            intent.putExtra("codigoEp", codigoEp)
            intent.putExtra("codigoAnime", codigoAnime)
            intent.putExtra("tipo", tipo)
            intent.putExtra("nomeAnime", nomeAnime)
            intent.putExtra("duracao", duracao)
            intent.putExtra("imagemAnime", imagemAnime)
            intent.putExtra("sinopse", sinopse)
            intent.putExtra("saga", saga)
            startActivity(intent)
        }
    }

    private fun carregarItens(
        nomeAnime: String?,
        titulo: String?,
        sinopse: String?,
        duracao: String?,
        tipo: String?,
        imagem: String?
    ) {
        when {
            tipo == "Filme" -> {
                tvTituloEpDet.visibility = GONE
                tvSinopseEpDet.text = sinopse
            }
            sinopse!!.isEmpty() -> {
                tvSinopseEpDet.text = "Sinopse Indisponível"
            }
            sinopse!!.isNotEmpty() -> {
                tvSinopseEpDet.text = sinopse
            }
        }
        tvNomeAnimeEpDet.text = nomeAnime
        tvTituloEpDet.text = titulo
        tvDuracaoEpDet.text = "${duracao}min"
        tvTipoEpDet.text = tipo
        Picasso.get().load(imagem).into(ivEpisodioDet)
    }
}