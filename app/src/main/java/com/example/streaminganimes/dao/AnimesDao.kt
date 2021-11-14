package com.example.streaminganimes.dao

import android.content.Context
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.adapters.*
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelHistorico
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.ArrayList

class AnimesDao{

    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!

    fun carregarCatalogo(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
        db.collection("animes").orderBy("nomeAnime", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        nomeInsensitive = document["nomeInsensitive"] as String,
                        sinopse = document["sinopse"] as String,
                        estudio = document["estudio"] as String,
                        ano = document["ano"] as String,
                        imagem = document["imagem"] as String,
                        genero = document["genero"] as String,
                        codigo = document["codigo"] as String,
                        tipo = document["tipo"] as String,
                        qtEpisodios = document["qtEpisodios"] as Long
                    )
                    arrayList.add(animes)
                    val adapter = AdapterCatalogo(arrayList)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimesPopulares(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView, textView: TextView){
         db.collection("animes").orderBy("nomeAnime", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
             if(task.isSuccessful){
                 for (document in task.result){
                     val animes = ModelAnimes(
                         nomeAnime = document["nomeAnime"] as String,
                         nomeInsensitive = document["nomeInsensitive"] as String,
                         sinopse = document["sinopse"] as String,
                         estudio = document["estudio"] as String,
                         ano = document["ano"] as String,
                         imagem = document["imagem"] as String,
                         genero = document["genero"] as String,
                         codigo = document["codigo"] as String,
                         tipo = document["tipo"] as String,
                         qtEpisodios = document["qtEpisodios"] as Long
                     )
                     arrayList.add(animes)
                     val adapter = AdapterAnimes(arrayList)
                     textView.visibility = VISIBLE
                     recyclerView.adapter = adapter
                 }
             }
         }
     }

    fun carregarAnimesRecentes(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView, textView: TextView){
        db.collection("animes").orderBy("ano", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for (document in task.result){
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        nomeInsensitive = document["nomeInsensitive"] as String,
                        sinopse = document["sinopse"] as String,
                        estudio = document["estudio"] as String,
                        ano = document["ano"] as String,
                        imagem = document["imagem"] as String,
                        genero = document["genero"] as String,
                        codigo = document["codigo"] as String,
                        tipo = document["tipo"] as String,
                        qtEpisodios = document["qtEpisodios"] as Long
                    )
                    arrayList.add(animes)
                    val adapter = AdapterAnimes(arrayList)
                    textView.visibility = VISIBLE
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimesAventura(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView, textView: TextView){
        db.collection("animes").orderBy("genero", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for (document in task.result){
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        nomeInsensitive = document["nomeInsensitive"] as String,
                        sinopse = document["sinopse"] as String,
                        estudio = document["estudio"] as String,
                        ano = document["ano"] as String,
                        imagem = document["imagem"] as String,
                        genero = document["genero"] as String,
                        codigo = document["codigo"] as String,
                        tipo = document["tipo"] as String,
                        qtEpisodios = document["qtEpisodios"] as Long
                    )
                    val genero = animes.genero
                    if(genero.contains("Aventura")){
                        arrayList.add(animes)
                    }
                    val adapter = AdapterAnimes(arrayList)
                    textView.visibility = VISIBLE
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimesAcao(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView, textView: TextView){
        db.collection("animes").orderBy("genero", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for (document in task.result){
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
                    val genero = animes.genero
                    if(genero.contains("Ação")){
                        arrayList.add(animes)
                    }
                    val adapter = AdapterAnimes(arrayList)
                    textView.visibility = VISIBLE
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimeSugerido1(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
        db.collection("animeSugeridos").orderBy("nomeAnime", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for (document in task.result){
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        sinopse = document["sinopse"] as String,
                        imagem = document["imagem"] as String,
                        codigo = document["codigo"] as String
                    )
                    arrayList.add(animes)
                    val adapter = AdapterAnimeSugerido(arrayList)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimeSugerido2(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
        db.collection("animeSugeridos").orderBy("nomeAnime", Query.Direction.ASCENDING).limit(1).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for (document in task.result){
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        sinopse = document["sinopse"] as String,
                        imagem = document["imagem"] as String,
                        codigo = document["codigo"] as String
                    )
                    arrayList.add(animes)
                    val adapter = AdapterAnimeSugerido(arrayList)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun buscarAnimes(nomeAnime: String, arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView , context: Context){
        db.collection("animes").orderBy("nomeInsensitive", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                arrayList.clear()
                for (document in task.result) {
                    val animes = ModelAnimes(
                        nomeAnime = document["nomeAnime"] as String,
                        nomeInsensitive = document["nomeInsensitive"] as String,
                        sinopse = document["sinopse"] as String,
                        estudio = document["estudio"] as String,
                        ano = document["ano"] as String,
                        imagem = document["imagem"] as String,
                        genero = document["genero"] as String,
                        codigo = document["codigo"] as String,
                        tipo = document["tipo"] as String,
                        qtEpisodios = document["qtEpisodios"] as Long
                    )
                    val nome = animes.nomeAnime
                    val insensitive = animes.nomeInsensitive
                    if (nome.contains(nomeAnime) || insensitive.contains(nomeAnime)) {
                        arrayList.add(animes)
                    } else {
                        Log.d("Animes", "Anime incompatível $nomeAnime")
                    }
                }
                val adapter = AdapterBuscaAnimes(arrayList, context)
                val count = adapter.itemCount
                if(count <1){
                    recyclerView.visibility = GONE
                }
                recyclerView.adapter = adapter
            }
        }
    }

    fun buscarAnimesSemLimite(nomeAnime: String, arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView , context: Context){
        db.collection("animes").get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
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
                    val nome = animes.nomeAnime
                    val insensitive = animes.nomeInsensitive
                    if(nome.contains(nomeAnime) || insensitive.contains(nomeAnime)){
                        arrayList.add(animes)
                        Toast.makeText(context, "Achou", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Log.d("Animes", "Anime incompatível $nomeAnime")
                    }
                }
//                val adapter = AdapterBuscaAnimes(arrayList)
//                val count = adapter.itemCount
//                if(count < 1){
//                    Toast.makeText(context, "Vazio", Toast.LENGTH_SHORT).show()
//                }
//                Toast.makeText(context, "Preenchido", Toast.LENGTH_SHORT).show()
//                recyclerView.adapter = adapter
            }
            else{
                Toast.makeText(context, "Erro inesperado", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun buscarAnimesPorGenero(arrayList: ArrayList<ModelAnimes>, genero: String, recyclerView: RecyclerView, context: Context){
        db.collection("animes").orderBy("nomeAnime", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
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
                    val gen = animes.genero
                    if(gen.contains(genero)){
                        arrayList.add(animes)
                    }
                    else{
                        Log.d("Animes", "Animes incompatíveis ${animes.nomeAnime}")
                    }
                    val adapter = AdapterCatalogo(arrayList)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}