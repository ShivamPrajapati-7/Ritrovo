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
import com.humanoide.ritrovo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.registerScrollView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Register button click
        binding.btnRegister.setOnClickListener {
            performRegistration()
        }

        // Navigate back to Login Activity
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun performRegistration() {
        val fullName = binding.etFullName.text?.toString()?.trim().orEmpty()
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString()?.trim().orEmpty()
        val confirmPassword = binding.etConfirmPassword.text?.toString()?.trim().orEmpty()

        // Clear previous errors
        binding.tilFullName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        // Input validation
        if (fullName.isEmpty()) {
            binding.tilFullName.error = getString(R.string.err_empty_name)
            binding.etFullName.requestFocus()
            return
        }

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

        if (password != confirmPassword) {
            binding.tilConfirmPassword.error = getString(R.string.err_password_mismatch)
            binding.etConfirmPassword.requestFocus()
            return
        }

        // Selected Role
        val selectedRole = if (binding.toggleGroupRole.checkedButtonId == R.id.btnRoleOwner) {
            "Canteen Owner"
        } else {
            "Student"
        }

        setLoading(true)

        // TODO: Replace this block with Firebase Authentication & Firestore user creation logic
        // E.g., FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)...
        // and saving user details (fullName, email, role) to Firestore collection "users"
        binding.root.postDelayed({
            setLoading(false)
            Toast.makeText(this, getString(R.string.msg_register_success), Toast.LENGTH_SHORT).show()

            // Navigate to Home Dashboard
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_EMAIL", email)
                putExtra("USER_NAME", fullName)
                putExtra("USER_ROLE", selectedRole)
            }
            startActivity(intent)
            finishAffinity()
        }, 1000)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
        }
    }
}