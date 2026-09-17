package com.humanoide.ritrovo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var myauth: FirebaseAuth
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        btn=findViewById(R.id.btnlogin)

        //btn click listener
        //pass email and pass in new var
//        myauth.createUserWithEmailAndPassword(email, pass).addOnSuccessListner{ user->
        //        Message "Regiter Successfully"
        //        }.addOnFailureListner{filed->
        //          message
        //        }

        btn.setOnClickListener {
            var emailinput=email.text.toString()
            var pass=password.text.toString()

            myauth.createUserWithEmailAndPassword(emailinput, pass).addOnSuccessListener { user->
                Toast.makeText(this,"Successfully Register",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { failed->
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
}