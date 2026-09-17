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

class RegisterActivity : AppCompatActivity() {

    private lateinit var myauth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth
        myauth = FirebaseAuth.getInstance()

        email = findViewById(R.id.regEmail)
        password = findViewById(R.id.regPassword)
        confirmPassword = findViewById(R.id.regConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val emailInput = email.text.toString().trim()
            val passInput = password.text.toString().trim()
            val confirmPassInput = confirmPassword.text.toString().trim()

            if (emailInput.isEmpty()) {
                email.error = "Email is required"
                return@setOnClickListener
            }

            if (passInput.isEmpty()) {
                password.error = "Password is required"
                return@setOnClickListener
            }

            if (passInput.length < 6) {
                password.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            if (passInput != confirmPassInput) {
                confirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            myauth.createUserWithEmailAndPassword(emailInput, passInput)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Registration Failed: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        }

        tvLogin.setOnClickListener {
            finish() // Close RegisterActivity and return to Login
        }
    }
}