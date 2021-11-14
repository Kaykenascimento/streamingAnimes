package com.example.streaminganimes.models

import com.google.firebase.Timestamp

class ModelUsuarios(
    val admin: Boolean,
    val dataCadastro: Timestamp,
    val emailUsuario: String,
    val imagemUsuario: String,
    val nomeUsuario: String,
    val sexo: String,
    val sobrenome: String) {
}