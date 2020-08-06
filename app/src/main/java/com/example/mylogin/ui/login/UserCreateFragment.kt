package com.example.mylogin.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mylogin.ErroFragment
import com.example.mylogin.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.new_user_fragment.*

class UserCreateFragment : Fragment() {

    companion object {
        fun newInstance() = UserCreateFragment()
    }

    private lateinit var auth: FirebaseAuth
    var usernameCreate: EditText? = null
    var passwordCreate: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.new_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        usernameCreate = view?.findViewById(R.id.usernameId)!!
        passwordCreate = view?.findViewById(R.id.passwordId)!!
        createUserFirebase()
    }

    fun createUserFirebase() {
        create_login.setOnClickListener {
            this.auth.createUserWithEmailAndPassword(
                usernameCreate?.text.toString(),
                passwordCreate?.text.toString()
            ).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val firebaseUser = this.auth.currentUser!!
                    Toast.makeText(activity, "$firebaseUser", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                } else {
                    var message = "Não foi possivel cadastrar esse usuario. Tente mais tarde"
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.container, ErroFragment.newInstance(message))
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
        }

    }
}