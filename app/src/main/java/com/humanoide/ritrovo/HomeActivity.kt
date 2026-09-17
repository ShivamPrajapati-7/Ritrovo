package com.humanoide.ritrovo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.humanoide.ritrovo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.homeMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val email = intent.getStringExtra("USER_EMAIL") ?: "user@campus.edu"
        val role = intent.getStringExtra("USER_ROLE") ?: "Student"
        val name = intent.getStringExtra("USER_NAME")

        if (role == "Canteen Owner") {
            binding.tvWelcomeTitle.text = getString(R.string.welcome_owner)
        } else {
            binding.tvWelcomeTitle.text = if (name != null) "Welcome, $name!" else getString(R.string.welcome_student)
        }

        binding.tvUserEmail.text = String.format("%s (%s)", email, role)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}