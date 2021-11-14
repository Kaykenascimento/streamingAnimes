package com.example.streaminganimes.dao

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View.GONE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.activitys.AnimeDetalhesActivity
import com.example.streaminganimes.activitys.AssistirEpActivity
import com.example.streaminganimes.activitys.EpisodiosDetalhesActivity
import com.example.streaminganimes.activitys.inicioActivity
import com.example.streaminganimes.adapters.AdapterEpisodios
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelEpisodios
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EpisodiosDao {
    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!

    private fun desembrulhar(context: Context): Activity? {
        var context: Context? = context
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }
        return context as Activity?
    }

    fun carregarEpisodios(codigoAnime: String, arrayList: ArrayList<ModelEpisodios>,
                          recyclerView: RecyclerView,
                          tipo: String,
                          textView: TextView, context: Context){
        db.collection("animes").document(codigoAnime).collection("episodios").orderBy("titulo", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val episodios = ModelEpisodios(
                        ano = document["ano"] as String,
                        codigo = document["codigo"] as String,
                        titulo = document["titulo"] as String,
                        sinopse = document["sinopse"] as String,
                        duracao = document["duracao"] as String,
                        imagem = document["imagem"] as String,
                        link = document["link"] as String,
                        filler = document["filler"] as Boolean,
                        saga = document["saga"] as String,
                    )
                    arrayList.add(episodios)
                    if(tipo == "Filme"){
                        textView.visibility = GONE
                    }
                    else if(arrayList.size <1){
                        textView.text = "Sem Episódios - Em breve"
                    }
                    val adapter = AdapterEpisodios(arrayList, tipo, context)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarProximoEp(codigoAnime: String,
                          tituloEp: String,
                          tipo: String,
                          nomeAnime: String,
                          imagem: String,
                          context: Context){
        db.collection("animes").document(codigoAnime).collection("episodios")
            .orderBy("titulo", Query.Direction.ASCENDING).startAfter(tituloEp).limit(1).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val episodio = ModelEpisodios(
                        titulo = document["titulo"] as String,
                        sinopse = document["sinopse"] as String,
                        duracao = document["duracao"] as String,
                        imagem = document["imagem"] as String,
                        filler = document["filler"] as Boolean,
                        saga = document["saga"] as String,
                        codigo = document["codigo"] as String,
                        ano = document["ano"] as String,
                        link = document["link"] as String
                    )
                    val activity = desembrulhar(context) as AppCompatActivity
                    val intent = Intent(activity, AssistirEpActivity::class.java)
                    intent.putExtra("titulo", episodio.titulo)
                    intent.putExtra("link", episodio.link)
                    intent.putExtra("codigoEp", episodio.codigo)
                    intent.putExtra("codigoAnime", codigoAnime)
                    intent.putExtra("duracao", episodio.duracao)
                    intent.putExtra("sinopse", episodio.sinopse)
                    intent.putExtra("tipo", tipo)
                    intent.putExtra("saga", episodio.saga)
                    intent.putExtra("nomeAnime", nomeAnime)
                    intent.putExtra("imagem", imagem)
                    activity.startActivity(intent)
                }
                if(task.result.isEmpty){
                    chamarTelaDetalhesAnimes(codigoAnime, context)
                }
            }
        }
    }

    fun carregarAssistirAgora(codigoAnime: String,
                              nomeAnime: String,
                              context: Context){
        db.collection("animes").document(codigoAnime).collection("episodios")
            .orderBy("titulo", Query.Direction.ASCENDING).limit(1).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val episodio = ModelEpisodios(
                        ano = document["ano"] as String,
                        codigo = document["codigo"] as String,
                        duracao = document["duracao"] as String,
                        filler = document["filler"] as Boolean,
                        imagem = document["imagem"] as String,
                        link = document["link"] as String,
                        saga = document["saga"] as String,
                        sinopse = document["sinopse"] as String,
                        titulo = document["titulo"] as String
                    )
                    val activity = desembrulhar(context) as AppCompatActivity
                    val intent = Intent(activity, EpisodiosDetalhesActivity::class.java)
                    intent.putExtra("titulo", episodio.titulo)
                    intent.putExtra("link", episodio.link)
                    intent.putExtra("codigoEp", episodio.codigo)
                    intent.putExtra("codigoAnime", codigoAnime)
                    intent.putExtra("tipo", "Anime")
                    intent.putExtra("nomeAnime", nomeAnime)
                    intent.putExtra("duracao", episodio.duracao)
                    intent.putExtra("imagem", episodio.imagem)
                    intent.putExtra("sinopse", episodio.sinopse)
                    intent.putExtra("saga", episodio.saga)
                    activity.startActivity(intent)
                }
            }
        }
    }

    fun chamarTelaDetalhesAnimes(codigoAnime: String,
                                 context: Context){
        db.collection("animes").whereEqualTo("codigo", codigoAnime).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for (document in task.result) {
                        val animes = ModelAnimes(
                            nomeAnime = document.data["nomeAnime"] as String,
                            nomeInsensitive = document.data["nomeInsensitive"] as String,
                            sinopse = document.data["sinopse"] as String,
                            estudio = document.data["estudio"] as String,
                            ano = document.data["ano"] as String,
                            imagem = document["imagem"] as String,
                            genero = document["genero"] as String,
                            codigo = document["codigo"] as String,
                            tipo = document["tipo"] as String,
                            qtEpisodios = document["qtEpisodios"] as Long
                        )
                        val activity = desembrulhar(context) as AppCompatActivity
                        val intent = Intent(activity, AnimeDetalhesActivity::class.java)
                        intent.putExtra("nomeAnime", animes.nomeAnime)
                        intent.putExtra("sinopse", animes.sinopse)
                        intent.putExtra("estudio", animes.estudio)
                        intent.putExtra("ano", animes.ano)
                        intent.putExtra("genero", animes.genero)
                        intent.putExtra("imagem", animes.imagem)
                        intent.putExtra("codigo", animes.codigo)
                        intent.putExtra("tipo", animes.tipo)
                        intent.putExtra("qtEps", animes.qtEpisodios)
                        intent.putExtra("tela", AssistirEpActivity::class.java.simpleName)
                        activity.startActivity(intent)
                    }
                }
                else if(!task.isSuccessful){
                    val activity = desembrulhar(context) as AppCompatActivity
                    val intent = Intent(activity, inicioActivity::class.java)
                    activity.startActivity(intent)
                    Toast.makeText(context, "Ocorreu um erro inesperado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
