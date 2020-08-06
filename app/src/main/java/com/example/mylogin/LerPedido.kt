package com.example.mylogin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LerPedido(
    var tipo:String,
    var preco:String,
    var quant:String
)

