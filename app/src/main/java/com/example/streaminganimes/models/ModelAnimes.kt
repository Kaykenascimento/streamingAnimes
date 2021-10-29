package com.example.streaminganimes.models

class ModelAnimes (val nomeAnime: String,
                   val nomeInsensitive: String,
                   val sinopse: String,
                   val estudio: String,
                   val ano: String,
                   val imagem: String,
                   val genero: String,
                   val codigo: String,
                   val tipo: String,
                   val qtEpisodios: Long) {

    constructor(nomeAnime: String, imagem: String, sinopse: String, codigo: String) : this (nomeAnime,"",sinopse,"","", imagem,"", codigo, "", 0)

}