package com.humanoide.ritrovo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private val myauth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val mainView = findViewById<LinearLayout>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Notification & Profile buttons
        findViewById<ImageButton>(R.id.btnNotification)?.setOnClickListener {
            Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnProfile)?.setOnClickListener {
            showProfileOptions()
        }

        // Order Now Button
        findViewById<Button>(R.id.btnOrderNow)?.setOnClickListener {
            Toast.makeText(this, "Select a canteen to order!", Toast.LENGTH_SHORT).show()
        }

        // Categories
        val categoryToast = { name: String ->
            Toast.makeText(this, "Filtering by $name", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.catSnacks)?.setOnClickListener { categoryToast("Snacks") }
        findViewById<LinearLayout>(R.id.catMainCourse)?.setOnClickListener { categoryToast("Main Course") }
        findViewById<LinearLayout>(R.id.catBeverages)?.setOnClickListener { categoryToast("Beverages") }
        findViewById<LinearLayout>(R.id.catHealthy)?.setOnClickListener { categoryToast("Healthy") }
        findViewById<LinearLayout>(R.id.catAll)?.setOnClickListener { categoryToast("All Items") }

        // Canteens
        val canteenToast = { name: String ->
            Toast.makeText(this, "Opening $name...", Toast.LENGTH_SHORT).show()
        }
        findViewById<MaterialCardView>(R.id.cardCanteen1)?.setOnClickListener { canteenToast("Central Canteen") }
        findViewById<MaterialCardView>(R.id.cardCanteen2)?.setOnClickListener { canteenToast("Cafe 360") }
        findViewById<MaterialCardView>(R.id.cardCanteen3)?.setOnClickListener { canteenToast("Food Junction") }
        findViewById<TextView>(R.id.tvViewCanteens)?.setOnClickListener { canteenToast("All Canteens") }

        // Food Items
        val foodToast = { name: String ->
            Toast.makeText(this, "Added $name to cart!", Toast.LENGTH_SHORT).show()
        }
        findViewById<MaterialCardView>(R.id.cardFood1)?.setOnClickListener { foodToast("Paneer Roll") }
        findViewById<MaterialCardView>(R.id.cardFood2)?.setOnClickListener { foodToast("Masala Maggi") }
        findViewById<MaterialCardView>(R.id.cardFood3)?.setOnClickListener { foodToast("Cold Coffee") }
        findViewById<MaterialCardView>(R.id.cardFood4)?.setOnClickListener { foodToast("Veg Burger") }
        findViewById<TextView>(R.id.tvViewPopular)?.setOnClickListener { foodToast("Popular Items") }

        // Bottom Navigation
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener { selectTab("Home") }
        findViewById<LinearLayout>(R.id.navOrders)?.setOnClickListener { selectTab("Orders") }
        findViewById<LinearLayout>(R.id.navCart)?.setOnClickListener { selectTab("Cart") }
        findViewById<LinearLayout>(R.id.navProfile)?.setOnClickListener { selectTab("Profile") }
    }

    private fun selectTab(tabName: String) {
        val activeOrange = 0xFFE85D04.toInt()
        val inactiveGray = 0xFF707070.toInt()

        val navHomeImg = findViewById<ImageView>(R.id.ivNavHome)
        val navHomeTv = findViewById<TextView>(R.id.tvNavHome)
        val navOrdersImg = findViewById<ImageView>(R.id.ivNavOrders)
        val navOrdersTv = findViewById<TextView>(R.id.tvNavOrders)
        val navCartImg = findViewById<ImageView>(R.id.ivNavCart)
        val navCartTv = findViewById<TextView>(R.id.tvNavCart)
        val navProfileImg = findViewById<ImageView>(R.id.ivNavProfile)
        val navProfileTv = findViewById<TextView>(R.id.tvNavProfile)

        // Reset all
        navHomeImg?.setColorFilter(inactiveGray)
        navHomeTv?.setTextColor(inactiveGray)
        navOrdersImg?.setColorFilter(inactiveGray)
        navOrdersTv?.setTextColor(inactiveGray)
        navCartImg?.setColorFilter(inactiveGray)
        navCartTv?.setTextColor(inactiveGray)
        navProfileImg?.setColorFilter(inactiveGray)
        navProfileTv?.setTextColor(inactiveGray)

        when (tabName) {
            "Home" -> {
                navHomeImg?.setColorFilter(activeOrange)
                navHomeTv?.setTextColor(activeOrange)
            }
            "Orders" -> {
                navOrdersImg?.setColorFilter(activeOrange)
                navOrdersTv?.setTextColor(activeOrange)
                Toast.makeText(this, "Orders screen", Toast.LENGTH_SHORT).show()
            }
            "Cart" -> {
                navCartImg?.setColorFilter(activeOrange)
                navCartTv?.setTextColor(activeOrange)
                Toast.makeText(this, "Cart screen", Toast.LENGTH_SHORT).show()
            }
            "Profile" -> {
                navProfileImg?.setColorFilter(activeOrange)
                navProfileTv?.setTextColor(activeOrange)
                showProfileOptions()
            }
        }
    }

    private fun showProfileOptions() {
        val user = myauth.currentUser
        val emailStr = user?.email ?: "Guest"

        AlertDialog.Builder(this)
            .setTitle("Account ($emailStr)")
            .setMessage("What would you like to do?")
            .setPositiveButton("Log Out") { _, _ ->
                myauth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
