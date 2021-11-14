package com.example.streaminganimes.fragments.conta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.CadastrarUsuarioActivity
import com.example.streaminganimes.activitys.LoginActivity

class ContaFragmentSemLogin : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_conta_sem_login, container, false)

        val buttonLogar = view.findViewById<Button>(R.id.btLoginFrag)
        val buttonCriarConta = view.findViewById<Button>(R.id.btCriarContaFrag)

        buttonLogar.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonCriarConta.setOnClickListener {
            val intent = Intent(activity, CadastrarUsuarioActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}