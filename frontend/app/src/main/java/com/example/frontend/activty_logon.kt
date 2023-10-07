package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class activty_logon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activty_logon)

        val tvLogin = findViewById<TextView>(R.id.signinRedirectText)

        tvLogin.setOnClickListener({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        })
    }
}