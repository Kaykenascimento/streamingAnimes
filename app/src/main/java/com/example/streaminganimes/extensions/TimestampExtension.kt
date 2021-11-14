package com.example.streaminganimes.extensions

import com.google.firebase.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Timestamp.formatarData(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val formato = SimpleDateFormat(formatoBrasileiro)
    return formato.format(this.toDate())
}

fun Timestamp.formatarDataEHora(): String{
    val formatoHoraEData = "dd/MM/yyyy hh:mm:ss"
    val formatoDataEHora = SimpleDateFormat(formatoHoraEData)
    return formatoDataEHora.format(this.toDate())
}
