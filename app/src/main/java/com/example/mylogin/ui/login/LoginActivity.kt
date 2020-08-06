package com.example.mylogin.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mylogin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hideSoftKeyboard
import kotlinx.android.synthetic.main.fragment_erro.*
import replaceFragmentInActivity

class LoginActivity : AppCompatActivity()  {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loading: ProgressBar


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        username = findViewById(R.id.usernameId)
        password = findViewById(R.id.passwordId)
        val login = findViewById<Button>(R.id.login)
        loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid
            if (login.isEnabled){
                login.background = getDrawable( R.drawable.fundo_layout_buttom)
            }

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {

            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            firebaseLogin()

        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }



            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
                hideSoftKeyboard()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.uid != "X7QUuqyy1ZQVc7d2quo9HpGmJkA3"){
                val intent = Intent(this, ManagerActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun firebaseLogin() {
        loading.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword("${username.text}", "${password.text}")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loading.visibility = View.GONE
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    loading.visibility = View.GONE
                    var text = "Erro ao logar. Verifique se digitou email ou senha erradas"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ErroFragment.newInstance(text))
                        .addToBackStack(null)
                        .commit()

                }

            }
    }


}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}