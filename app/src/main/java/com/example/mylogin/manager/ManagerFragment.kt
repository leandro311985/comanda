package com.example.mylogin.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylogin.Pedido
import com.example.mylogin.R
import com.example.mylogin.data.AuthListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.main_fragment2.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import startCreateUserActivity


class ManagerFragment : Fragment() , AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory: ManagerViewModelFactory by instance()

    companion object {
        fun newInstance() = ManagerFragment()
    }

    lateinit var auth: FirebaseAuth
    private lateinit var viewModel: ManagerViewModel
    var gamesMutableList: MutableList<Pedido> = mutableListOf()

    private lateinit var postKey: String
    private lateinit var postReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,factory).get(ManagerViewModel::class.java)
        setUpState()
        auth = FirebaseAuth.getInstance()
        postReference = FirebaseDatabase.getInstance().reference
        postKey = ""
        postReference = FirebaseDatabase.getInstance().reference
            .child("posts").child(postKey)
        val myUserId = auth.currentUser?.uid ?: ""


        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataValues in dataSnapshot.children) {
                    val post: Pedido? = dataValues.getValue(Pedido::class.java)
                    gamesMutableList.add(post!!)
                }
                recyclerManager.layoutManager = LinearLayoutManager(activity)
                recyclerManager.adapter = ManagerAdapter(gamesMutableList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "erro ao conectar com o banco firebase", Toast.LENGTH_SHORT).show()
            }
        }
        postReference.addListenerForSingleValueEvent(menuListener)


        createNewUser()
    }

    private fun setUpState(){
        viewModel.state.observeForever {state ->
            when(state){
                is ManagerViewModel.ScreenState.Loading -> {
                    Toast.makeText(activity, "loadind", Toast.LENGTH_SHORT).show()
                }
                is ManagerViewModel.ScreenState.Error -> {
                    Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
                }
                is ManagerViewModel.ScreenState.Sucess -> {
                    Toast.makeText(activity, "sucesso", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }


    fun createNewUser() {
        createUserId.setOnClickListener {
            context?.startCreateUserActivity()
        }
    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        gamesMutableList
    }

}