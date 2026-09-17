package com.humanoide.ritrovo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val root = findViewById<ConstraintLayout>(R.id.splash_main)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val logo = findViewById<ImageView>(R.id.logo)
        val tagline = findViewById<TextView>(R.id.tagline)

        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_text_anim)

        logo?.startAnimation(logoAnim)
        tagline?.startAnimation(textAnim)

        // Delay for 2.5 seconds to allow animations to complete
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500)
    }
}
