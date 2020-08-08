package com.example.mylogin

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}