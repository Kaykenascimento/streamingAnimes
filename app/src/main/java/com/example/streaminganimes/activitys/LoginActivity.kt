package com.example.streaminganimes.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.UsuarioDao
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val usuarioDao = UsuarioDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbarLogin)
        title = "Login"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_fechar)

        tvCriarConta.setOnClickListener {
            val intent = Intent(this, CadastrarUsuarioActivity::class.java)
            startActivity(intent)
        }

        tvRecuperarSenha.setOnClickListener {
            Toast.makeText(this, "Função indisponível", Toast.LENGTH_LONG).show()
        }

        btLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val senha = etSenhaLogin.text.toString()
            if(senha.length < 6){
                Toast.makeText(this, "Preencha os dados corretamente", Toast.LENGTH_SHORT).show()
            }
            else{
                usuarioDao.logarUsuarioNoAuth(email, senha, this)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}