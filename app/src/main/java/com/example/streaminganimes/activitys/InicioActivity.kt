package com.example.streaminganimes.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.streaminganimes.R
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.fragments.HomeFragment
import com.example.streaminganimes.fragments.conta.ContaFragment
import com.example.streaminganimes.fragments.conta.ContaFragmentSemLogin
import com.example.streaminganimes.fragments.favoritos.FavoritosFragment
import com.example.streaminganimes.fragments.favoritos.FavoritosSemLoginFragment
import com.example.streaminganimes.fragments.generos.GenerosFragment
import kotlinx.android.synthetic.main.activity_inicio.*

class inicioActivity : AppCompatActivity(){

    private val auth = ConfFireBase.firebaseAuth!!
    private val db = ConfFireBase.firebaseFirestore!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        setSupportActionBar(toolbarAnimes)
        title = ""

        btNavigation.setOnNavigationItemSelectedListener {
            val user = auth.currentUser
            when(it.itemId){
                R.id.home -> {
                    abrirTelaInicial()
                }
                R.id.favoritos -> {
                    if(user != null) {
                        abrirTelaFavoritos()
                    }else{
                        abrirTelaFavoritosSemLogin()
                    }
                }
                R.id.catalogo -> {
                    abrirTelaCatalogo()
                }
                R.id.conta -> {
                    if(user != null) {
                        abrirTelaConta()
                    }else{
                        abrirTelaContaSemLogin()
                    }
                }
            }
            true
        }

        btNavigation.selectedItemId = R.id.home

        btBuscarAnimes.setOnClickListener {
            val intent = Intent(this, BuscarAnimesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun abrirTelaContaSemLogin() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, ContaFragmentSemLogin())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("Minha conta")
        ivLogo.visibility = GONE
    }

    private fun abrirTelaConta() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, ContaFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("Minha conta")
        ivLogo.visibility = GONE
    }

    private fun abrirTelaCatalogo() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, GenerosFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("Catálogo")
        ivLogo.visibility = GONE
    }

    private fun abrirTelaFavoritosSemLogin() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, FavoritosSemLoginFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("Listas")
        ivLogo.visibility = GONE
    }

    private fun abrirTelaFavoritos() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, FavoritosFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("Listas")
        ivLogo.visibility = GONE
    }

    private fun abrirTelaInicial() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutPrincipal, HomeFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
        setarTitulo("")
        ivLogo.visibility = VISIBLE
    }

    private fun setarTitulo(titulo: String){
       toolbarAnimes.title = titulo
    }
}