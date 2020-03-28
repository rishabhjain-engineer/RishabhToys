package com.example.rishabhtoys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed( {
            val intent = Intent(SplashActivity@this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}
