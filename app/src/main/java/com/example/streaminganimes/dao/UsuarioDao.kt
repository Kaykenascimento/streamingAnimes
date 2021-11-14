package com.example.streaminganimes.dao

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.activitys.CadastrarUsuarioActivity
import com.example.streaminganimes.activitys.inicioActivity
import com.example.streaminganimes.adapteOrs.AdapterHistorico
import com.example.streaminganimes.adapters.AdapterContinueAssistindo
import com.example.streaminganimes.adapters.AdapterFavoritos
import com.example.streaminganimes.extensions.formatarData
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelFavoritos
import com.example.streaminganimes.models.ModelHistorico
import com.example.streaminganimes.models.ModelUsuarios
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList
import android.R
import android.view.View.GONE

import com.google.firebase.auth.FirebaseAuthUserCollisionException

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception


class UsuarioDao {

    private val db: FirebaseFirestore = ConfFireBase.firebaseFirestore!!
    private val auth: FirebaseAuth = ConfFireBase.firebaseAuth!!

    private fun desembrulhar(context: Context): Activity? {
        var context: Context? = context
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }
        return context as Activity?
    }

    fun inserirUsuarioNoAuth(usuario: ModelUsuarios, emailUsuario: String, senhaUsuario: String, context: Context) {
        auth.createUserWithEmailAndPassword(emailUsuario, senhaUsuario).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    inserirUsuarioNoFirestore(usuario, emailUsuario, senhaUsuario, context)
                }
                else{
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        Toast.makeText(context, "Senha fraca", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "Insira dados válidos", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "Usuário já existe", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro inesperado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    fun logarUsuarioNoAuth(emailUsuario: String, senhaUsuario: String, context: Context) {
        auth.signInWithEmailAndPassword(emailUsuario, senhaUsuario).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val activity = desembrulhar(context) as AppCompatActivity
                val intent = Intent(activity, inicioActivity::class.java)
                activity.startActivity(intent)
            } else {
                Toast.makeText(context, "Ocorreu um erro inesperado", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun inserirUsuarioNoFirestore(usuario: ModelUsuarios, email: String, senha: String, context: Context){
        db.collection("usuarios").document(auth.currentUser?.email!!).set(usuario).addOnSuccessListener { documentReference ->
            logarUsuarioNoAuth(email, senha, context)
        }.addOnFailureListener {
            Toast.makeText(context, "Falha ao cadastrar", Toast.LENGTH_LONG).show()
        }
    }

    fun deslogarUsuario(context: Context){
        auth.signOut()
        val activity = desembrulhar(context) as AppCompatActivity
        val intent = Intent(activity, inicioActivity::class.java)
        activity.startActivity(intent)
    }

        fun carregarUsuario(imageView: ImageView, tvNomeUsuario: TextView, tvEmailUsuario: TextView, tvDataUsuario: TextView) {
            db.collection("usuarios").whereEqualTo("emailUsuario", auth.currentUser?.email!!).limit(1).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for(document in task.result){
                        val usuario = ModelUsuarios(
                            admin = document["admin"] as Boolean,
                            dataCadastro = document["dataCadastro"] as Timestamp,
                            emailUsuario = document["emailUsuario"] as String,
                            imagemUsuario = document["imagemUsuario"] as String,
                            nomeUsuario = document["nomeUsuario"] as String,
                            sexo = document["sexo"] as String,
                            sobrenome = document["sobrenome"] as String
                        )
                        tvNomeUsuario.text = "Nome: ${usuario.nomeUsuario} ${usuario.sobrenome}"
                        tvEmailUsuario.text = "Email: ${usuario.emailUsuario}"
                        if(usuario.sexo == "Feminino"){
                            tvDataUsuario.text = "Usuária desde: ${usuario.dataCadastro.formatarData()}"
                        }else if(usuario.sexo == "Masculino") {
                            tvDataUsuario.text = "Usuário desde: ${usuario.dataCadastro.formatarData()}"
                        }
                        Picasso.get().load(usuario.imagemUsuario).into(imageView)
                    }
                }
            }
        }

        fun carregarHistorico(arrayList: ArrayList<ModelHistorico>, recyclerView: RecyclerView) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
                .orderBy("data", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val historico = ModelHistorico(
                                codigoEp = document["codigoEp"] as String,
                                codigoAnime = document["codigoAnime"] as String,
                                data = document["data"] as Timestamp,
                                duracao = document["duracao"] as String,
                                imagem = document["imagem"] as String,
                                link = document["link"] as String,
                                minutoAssistido = document["minutoAssistido"] as Long,
                                nomeAnime = document["nomeAnime"] as String,
                                saga = document["saga"] as String,
                                sinopseEp = document["sinopseEp"] as String,
                                tituloEp = document["tituloEp"] as String,
                                tipo = document["tipo"] as String,
                                status = document["status"] as String
                            )
                            arrayList.add(historico)
                            val adapter = AdapterHistorico(arrayList)
                            recyclerView.adapter = adapter
                        }
                    }
                }
            }

        fun inserirNoHistorico(historico: ModelHistorico) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
                .document("${historico.nomeAnime} - ${historico.tituloEp}").set(historico)
                .addOnSuccessListener { documentReference ->
                    Log.i("Historico", "Inserido no Historico")
                }.addOnFailureListener {
                    Log.i("Historico", "Falha ao inserir no histórico")
                }
            }

        fun carregarAnimesFavoritos(arrayList: ArrayList<ModelFavoritos>, recyclerView: RecyclerView) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos").orderBy("nomeAnime", Query.Direction.ASCENDING)
                .get()
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
                                codigoAnime = document["codigoAnime"] as String,
                                tipo = document["tipo"] as String,
                                qtEpisodios = document["qtEpisodios"] as Long
                            )
                            arrayList.add(favoritos)
                            val adapter = AdapterFavoritos(arrayList)
                            recyclerView.visibility = VISIBLE
                            recyclerView.adapter = adapter
                        }
                    }
                }
            }

        fun inserirAnimesFavoritos(favoritos: ModelFavoritos) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos")
                .document(favoritos.codigo).set(favoritos)
                .addOnSuccessListener { documentReference ->
                    Log.i("Favoritos", "Inserido nos favoritos")
                }.addOnFailureListener {
                    Log.i(
                        "Favoritos", "Falha ao inserir nos favoritos $it"
                    )
                }
            }

        fun deletarAnimesFavoritos(codigo: String) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos")
                .document(codigo).delete().addOnSuccessListener { documentReference ->
                    Log.i("Favoritos", "Deletado dos favoritos")
                }.addOnFailureListener {
                    Log.i("Favoritos", "Falha ao deletar dos favoritos")
                }
        }

        fun carregarContinueAssistindo(arrayList: ArrayList<ModelHistorico>, recyclerView: RecyclerView, textView: TextView) {
            db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
                .orderBy("data", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val historico = ModelHistorico(
                                codigoEp = document["codigoEp"] as String,
                                codigoAnime = document["codigoAnime"] as String,
                                data = document["data"] as Timestamp,
                                duracao = document["duracao"] as String,
                                imagem = document["imagem"] as String,
                                link = document["link"] as String,
                                minutoAssistido = document["minutoAssistido"] as Long,
                                nomeAnime = document["nomeAnime"] as String,
                                saga = document["saga"] as String,
                                sinopseEp = document["sinopseEp"] as String,
                                tituloEp = document["tituloEp"] as String,
                                tipo = document["tipo"] as String,
                                status = document["status"] as String
                            )
                            if(historico.status == "Incompleto"){
                                arrayList.add(historico)
                            }
                            val adapter = AdapterContinueAssistindo(arrayList)
                            if(adapter.itemCount < 1){
                                textView.visibility = GONE
                                recyclerView.visibility = GONE
                            }
                            else{
                                recyclerView.visibility = VISIBLE
                                textView.visibility = VISIBLE
                                recyclerView.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
