package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import javax.security.auth.login.LoginException

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase Completa")
        analytics.logEvent("InitScreen",bundle)

        setup()
    }
    private fun setup() {
        title="Autenticación"

        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
        loginButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailEditText.text.toString(),
                        passwordEditText.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }
    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido error al autenticar Usuarios")
        builder.setPositiveButton("Aceptar",null)
        val  dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

}