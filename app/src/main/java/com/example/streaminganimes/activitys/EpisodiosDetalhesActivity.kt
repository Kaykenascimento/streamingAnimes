package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.streaminganimes.R
import com.example.streaminganimes.firebase.ConfFireBase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episodios_detalhes.*

class EpisodiosDetalhesActivity : AppCompatActivity() {

    private val db = ConfFireBase.firebaseFirestore!!
    private val auth = ConfFireBase.firebaseAuth!!
    private val finalizado: String = "Você já assistiu este"
    private val naoFinalizado: String = "Você não finalizou este"
    private var minutoAssistido: Long = 0
    private var titulo: String? = ""
    private var nomeAnime: String? = ""
    private var sinopse: String? = ""
    private var duracao: String? = ""
    private var tipo: String? = ""
    private var imagem: String? = ""
    private var filler: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodios_detalhes)

        val bundle: Bundle ?= this.intent.extras
        nomeAnime = bundle?.getString("nomeAnime")
        titulo = bundle?.getString("titulo")
        sinopse = bundle?.getString("sinopse")
        duracao = bundle?.getString("duracao")
        tipo = bundle?.getString("tipo")
        imagem = bundle?.getString("imagem")
        filler = bundle?.getBoolean("filler")
        val imagemAnime = bundle?.getString("imagemAnime")
        val link = bundle?.getString("link")
        val codigoAnime = bundle?.getString("codigoAnime")
        val codigoEp = bundle?.getString("codigoEp")
        val saga = bundle?.getString("saga")

        fbIniciarEpisodio.setOnClickListener {
            val intent = Intent(this, AssistirEpActivity::class.java)
            intent.putExtra("titulo", titulo)
            intent.putExtra("link", link)
            intent.putExtra("codigoEp", codigoEp)
            intent.putExtra("codigoAnime", codigoAnime)
            intent.putExtra("tipo", tipo)
            intent.putExtra("nomeAnime", nomeAnime)
            intent.putExtra("duracao", duracao)
            intent.putExtra("imagem", imagem)
            intent.putExtra("sinopse", sinopse)
            intent.putExtra("saga", saga)
            if(minutoAssistido >1){
                intent.putExtra("minutoAssistido", minutoAssistido)
            }
            startActivity(intent)
        }

        cdComentarios.setOnClickListener {
            Toast.makeText(this, "Função indisponível no momento", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checarStatusEp(titulo: String,
                               nomeAnime: String,
                               sinopse: String,
                               duracao: String,
                               tipo: String,
                               imagem: String,
                               filler: Boolean){
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
            .whereEqualTo("tituloEp", titulo).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    if(task.result.isEmpty){
                        carregarItensNuncaAssistido(nomeAnime, titulo, sinopse, duracao, tipo, imagem, filler)
                    }
                    else{
                        for(document in task.result){
                            val status = document["status"] as String
                            minutoAssistido = document["minutoAssistido"] as Long
                            carregarItensAssistido(nomeAnime, titulo, sinopse, duracao, tipo, imagem, status, minutoAssistido, filler)
                        }
                    }
                }
            }
        }

    private fun carregarItensNuncaAssistido(
        nomeAnime: String?,
        titulo: String?,
        sinopse: String?,
        duracao: String?,
        tipo: String?,
        imagem: String?,
        filler: Boolean?,
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
        if(filler == true){
            tvTituloEpDet.text = "$titulo - Filler"
        }
        else{
            tvTituloEpDet.text = titulo
        }
        tvTituloEpDet.text = titulo
        tvDuracaoEpDet.text = "${duracao.toString()}min"
        tvTipoEpDet.text = tipo
        Picasso.get().load(imagem).into(ivEpisodioDet)
    }

    private fun carregarItensAssistido(
        nomeAnime: String?,
        titulo: String?,
        sinopse: String?,
        duracao: String?,
        tipo: String?,
        imagem: String?,
        status: String?,
        minutoAssistido: Long?,
        filler: Boolean?
    ){
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
        if(status == "Finalizado"){
            if(tipo == "Anime"){
                tvStatusEp.text = "$finalizado episódio"
            }
            else if(tipo == "Filme"){
                tvStatusEp.text = "$finalizado ${tipo.lowercase()}"
            }
            tvStatusEp.visibility = VISIBLE
            defaultTbEpDet.setDuration(duracao!!.toLong() * 60000)
            defaultTbEpDet.setPosition(minutoAssistido!!)
            defaultTbEpDet.isEnabled = false
        }
        else if(status == "Incompleto"){
            if(tipo == "Anime"){
                tvStatusEp.text = "$naoFinalizado episódio"
            }
            else if(tipo == "Filme"){
                tvStatusEp.text = "$naoFinalizado ${tipo.lowercase()}"
            }
            tvStatusEp.visibility = VISIBLE
            val duration = duracao!!.toDouble()
            defaultTbEpDet.setDuration((duration * 60000).toLong())
            defaultTbEpDet.setPosition(minutoAssistido!!)
            defaultTbEpDet.isEnabled = false
        }
        if(filler == true){
            tvTituloEpDet.text = "$titulo - Filler"
        }
        else{
            tvTituloEpDet.text = titulo
        }
        tvDuracaoEpDet.text = "${duracao.toString()}min"
        tvTipoEpDet.text = tipo
        Picasso.get().load(imagem).into(ivEpisodioDet)
    }

    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if(user != null){
            checarStatusEp(titulo!!, nomeAnime!!, sinopse!!, duracao!!, tipo!!, imagem!!, filler!!)
        }
        else{
            carregarItensNuncaAssistido(nomeAnime, titulo, sinopse, duracao, tipo, imagem, filler)
        }
    }
}