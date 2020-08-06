package com.example.mylogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylogin.ui.login.LoginActivity
import com.example.mylogin.ui.main.MainFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()



            auth = FirebaseAuth.getInstance()
            val nameUser = auth.currentUser?.email

            imageSair.setOnClickListener {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }


            toolbar.title = "Seja Bem vindo(a) $nameUser"
        }
    }
}