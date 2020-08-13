package com.example.mylogin.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.data.AuthListener
import com.example.mylogin.Dialog_components_Fragment
import com.example.mylogin.R
import com.example.mylogin.databinding.ActivityCreateUserBinding
import kotlinx.android.synthetic.main.activity_create_user.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CreateUserActivity : AppCompatActivity(),
    AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    var icon: Boolean = true

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
        dialogFragment(text,icon)

    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        text_email.clearComposingText()
        val text = "Usuatio cadastrado com sucesso"
        dialogFragment(text,icon)

    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        dialogFragment(message,false)
    }

    fun dialogFragment(message: String,icon: Boolean){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, Dialog_components_Fragment.newInstance(message,icon))
            .addToBackStack(null)
            .commit()
    }
}