package com.example.streaminganimes.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.streaminganimes.R
import com.example.streaminganimes.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_inicio.*

class inicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        btNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayoutPrincipal, HomeFragment())
                    transaction.disallowAddToBackStack()
                    transaction.commit()
                }
                R.id.favoritos -> Toast.makeText(this, "Favoritos", Toast.LENGTH_SHORT).show()
                R.id.catalogo -> Toast.makeText(this, "Catálogo", Toast.LENGTH_SHORT).show()
                R.id.conta -> Toast.makeText(this, "Conta", Toast.LENGTH_SHORT).show()
            }
            true
        }
        btNavigation.selectedItemId = R.id.home
    }
}