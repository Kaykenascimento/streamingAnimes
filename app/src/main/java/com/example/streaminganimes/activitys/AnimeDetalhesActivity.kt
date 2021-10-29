package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.extensions.formatarData
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelEpisodios
import com.example.streaminganimes.models.ModelFavoritos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_anime_detalhes.*
import java.util.*
import kotlin.collections.ArrayList

class AnimeDetalhesActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = ConfFireBase.firebaseAuth!!
    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!
    private var favorito: Boolean = false
    private var codigoFavorito: String = ""
    private var nomeAnimeInsert: String = ""
    private val usuarioDao = UsuarioDao()
    private val episodiosDao = EpisodiosDao()
    private val episodiosLista: ArrayList<ModelEpisodios> = ArrayList()
    private var tela: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_detalhes)

        val bundle: Bundle ?= this.intent.extras
        val nomeAnime = bundle?.getString("nomeAnime")!!
        val sinopse = bundle?.getString("sinopse")!!
        val estudio = bundle?.getString("estudio")!!
        val genero = bundle?.getString("genero")!!
        val ano = bundle?.getString("ano")!!
        val imagemAnime = bundle?.getString("imagem")!!
        val codigoAnime = bundle?.getString("codigo")!!
        val tipo = bundle?.getString("tipo")!!
        tela = bundle?.getString("tela")!!
        nomeAnimeInsert = nomeAnime

        checarAnimeFavorito(nomeAnime)
        carregarItens(imagemAnime, nomeAnime, sinopse, estudio, genero, ano, tipo)
        episodiosDao.carregarEpisodios(codigoAnime, episodiosLista, rcListaEpisodios, tipo, tvListaEpisodiosDet)

        ivAnimeFavorito.setOnClickListener{
            favorito = if(favorito){
                deletarAnimeFavorito()
                checarAnimeFavorito(nomeAnime)
                false
            } else{
                inserirAnimeFavorito(nomeAnime, sinopse, estudio, ano, imagemAnime, genero, auth, codigoAnime)
                checarAnimeFavorito(nomeAnime)
                true
            }
        }

        rcListaEpisodios.affectOnItemClicks { position, view ->
            val posicao = episodiosLista[position]
            val intent = Intent(this, EpisodiosDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", nomeAnime)
            intent.putExtra("titulo", posicao.titulo)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("duracao", posicao.duracao)
            intent.putExtra("tipo", tipo)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("link", posicao.link)
            intent.putExtra("codigoEp", posicao.codigo)
            intent.putExtra("saga", posicao.saga)
            intent.putExtra("codigoAnime", codigoAnime)
            intent.putExtra("imagemAnime", imagemAnime)
            startActivity(intent)
        }
    }

    private fun carregarItens(imagem: String?,
        nomeAnime: String?,
        sinopse: String?,
        estudio: String?,
        genero: String?,
        ano: String?, tipo: String?
    ) {
        Picasso.get().load(imagem).into(ivAnimesDet)
        tvNomeAnimeDet.text = nomeAnime
        tvSinopseDet.text = sinopse
        tvEstudioDet.text = "Estúdio: $estudio"
        tvGeneroDet.text = "Gênero(s) $genero"
        tvAnoDet.text = "Ano: $ano"
        tvQtEpisodios.text = "Tipo: $tipo"
    }

    fun checarAnimeFavorito(nomeAnime: String) {
        db.collection("usuarios").document("luffy@gmail.com").collection("favoritos")
            .whereEqualTo("nomeAnime", nomeAnime).limit(1).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        codigoFavorito = document["codigo"] as String
                        if(!task.result.isEmpty){
                            favorito = true
                            ivAnimeFavorito.setImageDrawable(resources.getDrawable(R.drawable.ic_anime_favorito_marcado))
                        }
                        else{
                            favorito = false
                            ivAnimeFavorito.setImageDrawable(resources.getDrawable(R.drawable.ic_anime_favorito_desmarcado))
                        }
                    }
                }
            }
        }

    private fun deletarAnimeFavorito(){
        usuarioDao.deletarAnimesFavoritos(codigoFavorito)
        ivAnimeFavorito.setImageDrawable(resources.getDrawable(R.drawable.ic_anime_favorito_desmarcado))
    }

    private fun inserirAnimeFavorito(nomeAnime: String,
                                     sinopse: String,
                                     estudio: String,
                                     ano: String,
                                     imagem: String,
                                     genero: String,
                                     auth: FirebaseAuth, codigoAnime: String){
        val id = db.collection("usuarios").document("luffy@gmail.com").collection("favoritos").document().id
        val favoritos = ModelFavoritos(
            nomeAnime = nomeAnime,
            sinopse = sinopse,
            estudio = estudio,
            ano = ano,
            imagem = imagem,
            genero = genero,
            emailUsuario = "luffy@gmail.com",
            dataAdicionado = Calendar.getInstance().formatarData(),
            codigo = id,
            codigoAnime = codigoAnime
        )
        usuarioDao.inserirAnimesFavoritos(favoritos)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }

    override fun onBackPressed() {
        if(tela == AssistirEpActivity::class.java.simpleName){
            val intent = Intent(this, inicioActivity::class.java)
            startActivity(intent)
        }
        else{
            super.onBackPressed()
        }
    }
}