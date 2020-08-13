package com.example.mylogin.data

class UserRepository(
    private val firebase: FirebaseSource
) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun saveData(userId: String,username: String, title: String, body: String,starCount:Int) =
        firebase.writeNewPost(userId = userId,username = username,title = title,body = body,starCount = starCount)

    fun responseDb(userId: String,user:String) = firebase.responseDb(user,userId)

    fun managerResponse(status: String) = firebase.managerResponse(status)


}