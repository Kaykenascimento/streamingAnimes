package com.example.streaminganimes.models

import com.google.firebase.Timestamp

class ModelHistorico(
    val codigoEp: String,
    val codigoAnime: String,
    val data: Timestamp,
    val duracao: String,
    val imagem: String,
    val link: String,
    val minutoAssistido: Long,
    val nomeAnime: String,
    val saga: String,
    val sinopseEp: String,
    val tituloEp: String,
    val tipo: String,
    val status: String)
{
}