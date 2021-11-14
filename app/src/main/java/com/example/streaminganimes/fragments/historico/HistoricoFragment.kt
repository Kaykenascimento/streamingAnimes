package com.example.streaminganimes.fragments.historico

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.AssistirEpActivity
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelHistorico
import kotlinx.android.synthetic.main.fragment_favoritos_lista.*

class HistoricoFragment : Fragment() {

    private val db = ConfFireBase.firebaseFirestore!!
    private val auth = ConfFireBase.firebaseAuth!!
    private val historicoLista: ArrayList<ModelHistorico> = ArrayList()
    private val usuarioDao = UsuarioDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_historico, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcHistorico)
        val clHistorico = view.findViewById<ConstraintLayout>(R.id.clHistoricoVazio)

        checarHistorico(historicoLista, recyclerView, clHistorico)

        recyclerView.affectOnItemClicks { position, _ ->
            val posicao = historicoLista[position]
            val intent = Intent(activity, AssistirEpActivity::class.java)
            intent.putExtra("titulo", posicao.tituloEp)
            intent.putExtra("link", posicao.link)
            intent.putExtra("codigoEp", posicao.codigoEp)
            intent.putExtra("codigoAnime", posicao.codigoAnime)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("duracao", posicao.duracao)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("sinopse", posicao.sinopseEp)
            intent.putExtra("saga", posicao.saga)
            intent.putExtra("minutoAssistido", posicao.minutoAssistido)
            startActivity(intent)
        }
        return view
    }

    private fun checarHistorico(historicoLista: ArrayList<ModelHistorico>, rcFavoritos: RecyclerView, clHistorico: ConstraintLayout){
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico").get().addOnSuccessListener { documentReference ->
            if(!documentReference.isEmpty){
                carregarHistorico(historicoLista, rcFavoritos)
            }
            else{
                clHistorico.visibility = VISIBLE
            }
        }
    }

    private fun carregarHistorico(historicoLista: ArrayList<ModelHistorico>, recyclerView: RecyclerView){
        usuarioDao.carregarHistorico(historicoLista, recyclerView)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}