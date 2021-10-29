package com.example.streaminganimes.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.adapters.AdapterContinueAssistindo
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelFavoritos
import com.example.streaminganimes.models.ModelHistorico
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList

class UsuarioDao {

    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!
    private val auth: FirebaseAuth = ConfFireBase.firebaseAuth!!

    fun carregarUsuario() {
        db.collection("usuarios").whereEqualTo("emailUsuario", auth.currentUser?.email!!).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var nomeUsuario: String = ""
                    var sobreNome: String = ""
                    var dataCadastro: Calendar = Calendar.getInstance()
                    var email: String = ""
                    var imagem: String = ""
                    var admin: Boolean = false
                    var sexo: String = ""
                    for (document in task.result) {
                        nomeUsuario = document["nomeUsuario"] as String
                        sobreNome = document["sobrenome"] as String
                        dataCadastro = document["dataCadastro"] as Calendar
                        email = document["emailUsuario"] as String
                        imagem = document["imagemUsuario"] as String
                        sexo = document["sexo"] as String
                        admin = document["admin"] as Boolean
                    }
                }
            }
        }

    fun carregarHistorico(arrayList: ArrayList<ModelHistorico>, recyclerView: RecyclerView) {
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val historico = ModelHistorico(
                            codigo = document["codigo"] as String,
                            data = document["data"] as String,
                            duracao = document["duracao"] as String,
                            imagem = document["imagem"] as String,
                            link = document["link"] as String,
                            minutoAssistido = document["minutoAssistindo"] as Long,
                            nomeAnime = document["nomeAnime"] as String,
                            saga = document["saga"] as String,
                            sinopseEp = document["sinopse"] as String,
                            tituloEp = document["titulo"] as String
                        )
                        arrayList.add(historico)
                    }
                }
            }
        }

    fun inserirNoHistorico(historico: ModelHistorico) {
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
            .document(historico.nomeAnime + " " + historico.tituloEp).set(historico)
            .addOnSuccessListener { documentReferente ->
                Log.i("Historico", "Inserido no Historico")
            }.addOnFailureListener {
            Log.i("Historico", "Falha ao inserir no histórico")
        }
    }

    fun carregarAnimesFavoritos(arrayList: ArrayList<ModelFavoritos>, recyclerView: RecyclerView) {
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val favoritos = ModelFavoritos(
                            nomeAnime = document["nomeAnime"] as String,
                            sinopse = document["sinopse"] as String,
                            estudio = document["estudio"] as String,
                            ano = document["ano"] as String,
                            imagem = document["imagem"] as String,
                            genero = document["genero"] as String,
                            codigo = document["codigo"] as String,
                            dataAdicionado = document["dataAdicionado"] as String,
                            emailUsuario = document["emailUsuario"] as String,
                            codigoAnime = document["codigoAnime"] as String
                        )
                        arrayList.add(favoritos)
                    }
                }
            }
        }

    fun inserirAnimesFavoritos(favoritos: ModelFavoritos) {
        db.collection("usuarios").document("luffy@gmail.com").collection("favoritos")
            .document(favoritos.codigo).set(favoritos).addOnSuccessListener { documentReference ->
            Log.i("Favoritos", "Inserido nos favoritos")
        }.addOnFailureListener {
            Log.i(
                "Favoritos", "Falha ao inserir nos favoritos $it"
            )
        }
    }

    fun deletarAnimesFavoritos(codigo: String) {
        db.collection("usuarios").document("luffy@gmail.com").collection("favoritos")
            .document(codigo).delete().addOnSuccessListener { documentReferece ->
                Log.i("Favoritos", "Deletado dos favoritos")
            }.addOnFailureListener {
                Log.i("Favoritos", "Falha ao deletar dos favoritos")
            }
        }

    fun carregarContinueAssistindo(arrayList: ArrayList<ModelHistorico>, recyclerView: RecyclerView) {
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
            .orderBy("data", Query.Direction.DESCENDING).limit(1).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val historico = ModelHistorico(
                            codigo = document["codigo"] as String,
                            data = document["data"] as String,
                            duracao = document["duracao"] as String,
                            imagem = document["imagem"] as String,
                            link = document["link"] as String,
                            minutoAssistido = document["minutoAssistido"] as Long,
                            nomeAnime = document["nomeAnime"] as String,
                            saga = document["saga"] as String,
                            sinopseEp = document["sinopseEp"] as String,
                            tituloEp = document["tituloEp"] as String
                        )
                        arrayList.add(historico)
                        val adapter = AdapterContinueAssistindo(arrayList)
                        recyclerView.adapter = adapter
                    }
                }
            }
    }
}