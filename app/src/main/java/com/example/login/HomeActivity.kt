package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle :Bundle? = intent.extras
        val email :String? = bundle?.getString("email")
        val provider :String? =bundle?.getString("provider")

        setup(email ?:"", provider?:"")
    }
    private fun setup(email: String, provider: String){
        title="Inicio"
        emailText.text = email
        providerText.text = provider

        logOutButton.setOnClickListener { 
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

}