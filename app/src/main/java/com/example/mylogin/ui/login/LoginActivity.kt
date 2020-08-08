package com.example.mylogin.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.AuthListener
import com.example.mylogin.ErroFragment
import com.example.mylogin.R
import com.example.mylogin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import startHomeActivity
import startManagerActivity

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    lateinit var auth:FirebaseAuth


    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        auth = FirebaseAuth.getInstance()

        viewModel.authListener = this
    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        if (auth.currentUser?.uid == MANAGER){
            startManagerActivity()
        }else
          startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ErroFragment.newInstance(message))
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            if (it.uid == MANAGER){
                startManagerActivity()
            }else
            startHomeActivity()
        }
    }
    companion object{
        val MANAGER = "WVOcETZW0vVfyTBxJu94zVELQMU2"
    }
}