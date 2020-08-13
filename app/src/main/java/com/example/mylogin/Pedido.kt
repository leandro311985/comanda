package com.example.mylogin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Pedido(
    var uid: Any? = "",
    var author: Any? = "",
    var title: Any? = "",
    var body: Any? = "",
    var starCount: Int = 0

    )


