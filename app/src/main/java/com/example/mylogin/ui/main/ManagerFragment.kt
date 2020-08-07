package com.example.mylogin.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.R
import com.example.mylogin.ui.login.UserCreateFragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.main_fragment2.*

class ManagerFragment : Fragment() {

    companion object {
        fun newInstance() = ManagerFragment()
    }

    private lateinit var postReference: DatabaseReference

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        createNewUser()
        postReference = FirebaseDatabase.getInstance().reference
            .child("users").child("001").child("pedido")


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.value
                message.text = post.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)

    }

    fun createNewUser() {
        createUserId.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, UserCreateFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

    }

}