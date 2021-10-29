package com.example.streaminganimes.extensions

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatarData(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val formato = SimpleDateFormat(formatoBrasileiro)
    return formato.format(this.time)
}