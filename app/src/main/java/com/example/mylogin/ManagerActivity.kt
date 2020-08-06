package com.example.mylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylogin.ui.login.LoginActivity
import com.example.mylogin.ui.main.ManagerFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.manager_activity.*

class ManagerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manager_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ManagerFragment.newInstance())
                .commitNow()
            auth = FirebaseAuth.getInstance()


            imageSairGerente.setOnClickListener {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}