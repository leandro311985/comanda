package com.example.mylogin.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mylogin.ErroFragment
import com.example.mylogin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.new_user_fragment.*

class UserCreateFragment : Fragment() {

    companion object {
        fun newInstance() = UserCreateFragment()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var login : LoginActivity
    private lateinit var loading: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        createUserFirebase()
        login = LoginActivity()
        loading = view?.findViewById(R.id.loading)!!

    }

    fun createUserFirebase() {
        create_login.setOnClickListener {
            loading.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(create_username.text.toString(),create_passwordId.text.toString())
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        loading.visibility = View.GONE
                        val user = auth.currentUser

                        Toast.makeText(activity, "Authentication sucesso-------"+ user,
                            Toast.LENGTH_SHORT).show()
                        activity?.supportFragmentManager?.popBackStack()

                    } else {
                        loading.visibility = View.GONE
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.container, ErroFragment.newInstance(task.exception.toString()))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                }
        }

    }
}