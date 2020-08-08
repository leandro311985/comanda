package com.example.mylogin.manager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylogin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.manager_activity.*
import startLoginActivity

class ManagerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manager_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    ManagerFragment.newInstance()
                )
                .commitNow()
            auth = FirebaseAuth.getInstance()

            toolbar.title ="Gerente ->" + auth.currentUser?.email
            imageSairGerente.setOnClickListener {
                auth.signOut()
                startLoginActivity()
                finish()
            }
        }
    }
}