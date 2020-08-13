package com.example.mylogin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylogin.data.AuthListener
import com.example.mylogin.R
import com.example.mylogin.ui.login.AuthViewModelFactory
import com.example.mylogin.ui.login.LoginActivity
import com.github.loadingview.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), AuthListener, KodeinAware {


    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: LoadingDialog
    private lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        val binding: ActivityLoginBinding =
//            DataBindingUtil.setContentView(this, R.layout.main_activity)
//        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()

            signOut()


            auth = FirebaseAuth.getInstance()
            val nameUser = auth.currentUser?.email
            toolbar.title = "Seja Bem vindo(a) $nameUser"
        }
    }

    fun signOut(){
        imageSair.setOnClickListener {
            dialog = LoadingDialog.get(this).show()
            loadingView.start()
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            loadingView.stop()
            finish()
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


}