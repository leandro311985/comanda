package com.example.mylogin.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.AuthListener
import com.example.mylogin.ErroFragment
import com.example.mylogin.R
import com.example.mylogin.databinding.ActivityCreateUserBinding
import kotlinx.android.synthetic.main.activity_create_user.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CreateUserActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreateUserBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_create_user)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
        val text = "Usuário cadastrado com sucesso"
        dialogFragment(text)

    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        text_email.clearComposingText()
        val text = "Usuatio cadastrado com sucesso"
        dialogFragment(text)

    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        dialogFragment(message)
    }

    fun dialogFragment(message: String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ErroFragment.newInstance(message))
            .addToBackStack(null)
            .commit()
    }
}