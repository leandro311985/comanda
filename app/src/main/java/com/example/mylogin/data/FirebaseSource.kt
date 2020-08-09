package com.example.mylogin.data

import com.example.mylogin.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable

class FirebaseSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }


    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    fun writeNewPost(userId: String, username: String, title: String, body: String) =
        Completable.create { emitter ->

            if (!emitter.isDisposed) {
                val key = database.child("pedido").push().key
                if (key == null) {
                    return@create
                }

                val post = Post(userId, username, title, body)
                val postValues = post.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "/posts/$key" to postValues,
                    "/user-posts/$userId/$key" to postValues
                )

                database.updateChildren(childUpdates)
            }
        }

    fun responseDb(userId:String,user:String)= Completable.create{ emitter ->
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                emitter.onComplete()

            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }
}