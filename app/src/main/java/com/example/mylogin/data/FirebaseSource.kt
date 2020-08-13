package com.example.mylogin.data

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylogin.Pedido
import com.example.mylogin.Post
import com.example.mylogin.manager.ManagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.Completable
import kotlinx.android.synthetic.main.main_fragment2.*

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

    fun managerResponse(status:String) =  Completable.create { emitter->
        if (!emitter.isDisposed) {
            var userId = firebaseAuth.currentUser?.uid ?:""
            database.child("posts").child(userId).setValue(status)
            emitter.onComplete()
        }

    }

    fun writeNewPost(userId: String, username: String, title: String, body: String,starCount:Int) =
        Completable.create { emitter ->

            if (!emitter.isDisposed) {
                val key = database.child("posts").push().key
                if (key == null) {
                    return@create
                }

                val post = Post(userId, username, title, body,starCount)
                val postValues = post.toMap()

                val childUpdates = hashMapOf<String, Any>(
                    "/posts/$key" to postValues
                )

                database.updateChildren(childUpdates)
            }
        }

    fun responseDb(userId:String,user:String)= Completable.create{ emitter ->
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataValues in dataSnapshot.children) {
                    val post: Pedido? = dataValues.getValue(Pedido::class.java)
                    if (!emitter.isDisposed) {
                            emitter.onComplete()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                emitter.onError(databaseError.toException())
            }
        }
        database.addListenerForSingleValueEvent(menuListener)
    }
}