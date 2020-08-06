package com.example.mylogin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Pedido(
    var pedido: Description?
)

data class Description(
    var tipo:String,
    var preco:String,
    var quant:String
)
