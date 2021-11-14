package com.example.streaminganimes.fragments.generos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.ResultadoBuscaAnimeActivity
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.GenerosDao
import com.example.streaminganimes.models.ModelGeneros

class GenerosListaFragment : Fragment() {

    private val generosLista: ArrayList<ModelGeneros> = ArrayList()
    private val generosDao = GenerosDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_generos_lista, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcGeneros)

        carregarGeneros(generosLista, recyclerView)

        recyclerView.affectOnItemClicks { position, view ->
            val posicao = generosLista[position]
            val intent = Intent(activity, ResultadoBuscaAnimeActivity::class.java)
            intent.putExtra("genero", posicao.nomeGenero)
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
            startActivity(intent)
        }

        return view
    }

    private fun carregarGeneros(generosLista: ArrayList<ModelGeneros>, recyclerView: RecyclerView){
        generosDao.carregarGeneros(generosLista, recyclerView)
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}