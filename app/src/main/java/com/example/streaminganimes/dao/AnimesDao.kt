package com.example.streaminganimes.dao

import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.adapters.AdapterAnimeSugerido
import com.example.streaminganimes.adapters.AdapterAnimes
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelAnimes
import com.example.streaminganimes.models.ModelHistorico
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.ArrayList

class AnimesDao(){

    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!

    fun carregarAnimesPopulares(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
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
                     recyclerView.adapter = adapter
                 }
             }
         }
     }

    fun carregarAnimesRecentes(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
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
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimesAventura(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
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
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun carregarAnimesAcao(arrayList: ArrayList<ModelAnimes>, recyclerView: RecyclerView){
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
}