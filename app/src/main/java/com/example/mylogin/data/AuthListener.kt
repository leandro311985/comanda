package com.example.mylogin.data

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}