package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.extensions.formatarData
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelEpisodios
import com.example.streaminganimes.models.ModelFavoritos
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_anime_detalhes.*
import kotlinx.android.synthetic.main.activity_episodios_detalhes.*
import java.util.*
import kotlin.collections.ArrayList

class AnimeDetalhesActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = ConfFireBase!!.firebaseAuth!!
    private val db: FirebaseFirestore = ConfFireBase!!.firebaseFirestore!!
    private var favorito: Boolean = false
    private var codigoFavorito: String = ""
    private val usuarioDao = UsuarioDao()
    private val episodiosDao = EpisodiosDao()
    private val episodiosLista: ArrayList<ModelEpisodios> = ArrayList()
    private var tela: String = ""
    private var nomeAnime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_detalhes)

        val bundle: Bundle ?= this.intent.extras
        nomeAnime = bundle?.getString("nomeAnime")!!
        val sinopse = bundle?.getString("sinopse")!!
        val estudio = bundle?.getString("estudio")!!
        val genero = bundle?.getString("genero")!!
        val ano = bundle?.getString("ano")!!
        val imagemAnime = bundle?.getString("imagem")!!
        val codigoAnime = bundle?.getString("codigo")!!
        val tipo = bundle?.getString("tipo")!!
        val qtEpisodios = bundle?.getLong("qtEps")!!
        tela = bundle?.getString("tela")!!

        carregarItens(imagemAnime, nomeAnime, sinopse, estudio, genero, ano, tipo, qtEpisodios!!)
        episodiosDao.carregarEpisodios(codigoAnime, episodiosLista, rcListaEpisodios, tipo, tvListaEpisodiosDet, this)

        ivAnimeFavorito.setOnClickListener{
            favorito = if(favorito){
                deletarAnimeFavorito()
                checarAnimeFavorito(nomeAnime)
                false
            } else{
                inserirAnimeFavorito(nomeAnime, sinopse, estudio, ano, imagemAnime, genero, auth, codigoAnime, tipo, qtEpisodios)
                checarAnimeFavorito(nomeAnime)
                true
            }
        }

        btVoltarDet.setOnClickListener {
            if(tela == AssistirEpActivity::class.java.simpleName) {
                val intent = Intent(this, inicioActivity::class.java)
                startActivity(intent)
            }
            onBackPressed()
        }

        rcListaEpisodios.affectOnItemClicks { position, view ->
            val posicao = episodiosLista[position]
            val intent = Intent(this, EpisodiosDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", nomeAnime)
            intent.putExtra("titulo", posicao.titulo)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("duracao", posicao.duracao)
            intent.putExtra("filler", posicao.filler)
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

    private fun carregarItens(
        imagem: String?,
        nomeAnime: String?,
        sinopse: String?,
        estudio: String?,
        genero: String?,
        ano: String?,
        tipo: String?,
        qtEps: Long
    ) {
        Picasso.get().load(imagem).into(ivAnimesDet)
        tvNomeAnimeDet.text = nomeAnime
        tvSinopseDet.text = sinopse
        tvEstudioDet.text = "Estúdio: $estudio"
        tvGeneroDet.text = "Gênero(s) $genero"
        tvAnoDet.text = "Ano: $ano"
        if(tipo == "Filme"){
            tvQtEpisodios.text = "Tipo: $tipo"
        }
        else if(tipo == "Anime") {
            tvQtEpisodios.text = "Quantidade de episódios: $qtEps\n\nTipo: $tipo"
        }
    }

    private fun checarAnimeFavorito(nomeAnime: String) {
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos")
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
                                     auth: FirebaseAuth,
                                     codigoAnime: String,
                                     tipo: String,
                                     qtEpisodios: Long){
        val id = db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos").document().id
        val favoritos = ModelFavoritos(
            nomeAnime = nomeAnime,
            sinopse = sinopse,
            estudio = estudio,
            ano = ano,
            imagem = imagem,
            genero = genero,
            emailUsuario = auth.currentUser?.email!!,
            dataAdicionado = Timestamp.now().formatarData(),
            codigo = id,
            codigoAnime = codigoAnime,
            tipo = tipo,
            qtEpisodios = qtEpisodios

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

    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if(user != null){
            checarAnimeFavorito(nomeAnime)
            ivAnimeFavorito.visibility = VISIBLE
        }
        else{
            ivAnimeFavorito.visibility = INVISIBLE
        }
    }
}