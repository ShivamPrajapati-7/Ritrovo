package com.humanoide.ritrovo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var myauth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (myauth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        myauth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnLogin = findViewById(R.id.btnlogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val emailInput = email.text.toString().trim()
            val passInput = password.text.toString().trim()

            if (emailInput.isEmpty()) {
                email.error = "Email is required"
                return@setOnClickListener
            }

            if (passInput.isEmpty()) {
                password.error = "Password is required"
                return@setOnClickListener
            }

            myauth.signInWithEmailAndPassword(emailInput, passInput)
                .addOnSuccessListener {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Login Failed: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}