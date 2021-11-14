package com.example.streaminganimes.fragments.favoritos

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.AnimeDetalhesActivity
import com.example.streaminganimes.adapters.AdapterFavoritos
import com.example.streaminganimes.classes.RecyclerItemClickListener
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelFavoritos
import kotlinx.android.synthetic.main.fragment_favoritos_lista.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritosListaFragment : Fragment() {

    private val usuarioDao = UsuarioDao()
    private val favoritosLista: ArrayList<ModelFavoritos> = ArrayList()
    private val db = ConfFireBase.firebaseFirestore!!
    private val auth = ConfFireBase.firebaseAuth!!
    private var teste: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favoritos_lista, container, false)

        val rcFavorito = view.findViewById<RecyclerView>(R.id.rcFavoritos)
        val clVazio = view.findViewById<ConstraintLayout>(R.id.clVazio)

        checarFavoritos(rcFavorito, favoritosLista, clVazio)

        rcFavorito.affectOnItemClicks { position, view ->
            val posicao = favoritosLista[position]
            val intent = Intent(activity, AnimeDetalhesActivity::class.java)
            intent.putExtra("nomeAnime", posicao.nomeAnime)
            intent.putExtra("sinopse", posicao.sinopse)
            intent.putExtra("estudio", posicao.estudio)
            intent.putExtra("ano", posicao.ano)
            intent.putExtra("genero", posicao.genero)
            intent.putExtra("imagem", posicao.imagem)
            intent.putExtra("codigo", posicao.codigoAnime)
            intent.putExtra("tipo", posicao.tipo)
            intent.putExtra("qtEps", posicao.qtEpisodios)
            intent.putExtra("tela", activity?.javaClass!!.simpleName)
            startActivity(intent)
        }

        return view
    }

    private fun carregarFavoritos(favoritosLista: ArrayList<ModelFavoritos>, rcFavorito: RecyclerView){
        usuarioDao.carregarAnimesFavoritos(favoritosLista, rcFavorito)
    }

    private fun deletarFavoritos(codigoFavorito: String){
        usuarioDao.deletarAnimesFavoritos(codigoFavorito)
    }

    private fun checarFavoritos(rcFavorito: RecyclerView, favoritosLista: ArrayList<ModelFavoritos>, clVazio: ConstraintLayout){
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("favoritos").get().addOnSuccessListener { documentReference ->
            if(!documentReference.isEmpty){
                carregarFavoritos(favoritosLista, rcFavorito)
            }
            else{
                clVazio.visibility = VISIBLE
            }
        }
    }

    @JvmOverloads
    fun RecyclerView.affectOnItemClicks(onClick: ((position: Int, view: View) -> Unit)? = null, onLongClick: ((position: Int, view: View) -> Unit)? = null) {
        this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
    }
}