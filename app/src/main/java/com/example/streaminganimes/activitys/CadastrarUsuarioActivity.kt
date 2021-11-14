package com.example.streaminganimes.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.models.ModelUsuarios
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_cadastrar_usuario.*

class CadastrarUsuarioActivity : AppCompatActivity() {

    private val usuarioDao = UsuarioDao()
    private var genero: String = ""
    private var imagem: String = ""
    private var checked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_usuario)

        setSupportActionBar(toolbarCadastro)
        title = "Cadastro"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_fechar)

        radioGroupSexo.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbFeminino -> {genero = "Feminino"; imagem = "https://i.pinimg.com/originals/7b/46/f3/7b46f36226cb5217e119e9566b37e0f4.jpg"; checked = true}
                R.id.rbMasculino -> {genero = "Masculino"; imagem = "https://pbs.twimg.com/media/Eb4Xl6GX0AA5GZ2.jpg"; checked = true}
            }
        }

        btCadastrarUser.setOnClickListener {
            val email = etEmailCad.text.toString()
            val senha = etSenhaCad.text.toString()
            val nome = etNomeUsuarioCad.text.toString()
            val sobrenome = etSobrenomeCad.text.toString()
            val usuario = ModelUsuarios(false, Timestamp.now(), email, imagem, nome, genero, sobrenome)
            if(radioGroupSexo.checkedRadioButtonId == -1){
                Toast.makeText(this, "Marque um dos sexos", Toast.LENGTH_SHORT).show()
            }
            else{
                usuarioDao.inserirUsuarioNoAuth(usuario, email, senha, this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}