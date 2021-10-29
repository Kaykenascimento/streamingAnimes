package com.example.streaminganimes.models

import com.google.firebase.Timestamp

class ModelHistorico(
    val codigo: String,
    val data: String,
    val duracao: String,
    val imagem: String,
    val link: String,
    val minutoAssistido: Long,
    val nomeAnime: String,
    val saga: String,
    val sinopseEp: String,
    val tituloEp: String)
{
}