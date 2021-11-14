package com.example.streaminganimes.dao

import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.adapters.AdapterGeneros
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelGeneros
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GenerosDao {

    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!

    fun carregarGeneros(arrayList: ArrayList<ModelGeneros>, recyclerView: RecyclerView){
        db.collection("generos").orderBy("nomeGenero", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(document in task.result){
                    val generos = ModelGeneros(
                        nomeGenero = document["nomeGenero"] as String,
                        imagem = document["imagem"] as String
                    )
                    arrayList.add(generos)
                    val adapter = AdapterGeneros(arrayList)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}