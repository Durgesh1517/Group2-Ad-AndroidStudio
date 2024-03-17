package com.example.jobshunt.IntroPageActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.jobshunt.LoginRegisterActivity.LoginActivity
import com.example.jobshunt.R

class IntroActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Handler to delay transition to CandidateActivity
        Handler().postDelayed({
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
