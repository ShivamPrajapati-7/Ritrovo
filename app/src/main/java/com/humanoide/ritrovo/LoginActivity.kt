package com.humanoide.ritrovo

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.humanoide.ritrovo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.loginScrollView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Login button
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        // Navigate to Register Activity
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Forgot Password click
        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset will be sent to your email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()

        // Clear errors
        binding.tilEmail.error = null
        binding.tilPassword.error = null

        // Input validation
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.err_empty_email)
            binding.etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.err_invalid_email)
            binding.etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.err_empty_password)
            binding.etPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            binding.tilPassword.error = getString(R.string.err_short_password)
            binding.etPassword.requestFocus()
            return
        }

        // Selected Role
        val selectedRole = if (binding.toggleGroupRole.checkedButtonId == R.id.btnRoleOwner) {
            "Canteen Owner"
        } else {
            "Student"
        }

        // Show loading state
        setLoading(true)

        // TODO: Replace this block with Firebase / Firestore Authentication logic
        // E.g., FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)...
        binding.root.postDelayed({
            setLoading(false)
            Toast.makeText(this, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show()

            // Navigate to Home Dashboard
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_EMAIL", email)
                putExtra("USER_ROLE", selectedRole)
            }
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
        }
    }
}