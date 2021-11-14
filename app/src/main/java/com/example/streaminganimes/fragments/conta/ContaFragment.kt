package com.example.streaminganimes.fragments.conta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.UsuarioDao
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContaFragment : Fragment() {

    private val usuarioDao = UsuarioDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_conta, container, false)

        val ivUsuario = view.findViewById<ImageView>(R.id.ivUsuario)
        val nomeUsuario = view.findViewById<TextView>(R.id.tvNomeUsuario)
        val emailUsuario = view.findViewById<TextView>(R.id.tvEmailUsuario)
        val data = view.findViewById<TextView>(R.id.tvDataUsuario)
        val buttonDeslogar = view.findViewById<Button>(R.id.btDeslogar)
        val fabImagem = view.findViewById<FloatingActionButton>(R.id.fabAlterarImagem)

        buttonDeslogar.setOnClickListener {
            activity?.let { it1 -> usuarioDao.deslogarUsuario(it1) }
        }

        fabImagem.setOnClickListener {
            Toast.makeText(activity, "Função indisponível no momento", Toast.LENGTH_SHORT).show()
        }

        usuarioDao.carregarUsuario(ivUsuario, nomeUsuario, emailUsuario, data)

        return view
    }
}