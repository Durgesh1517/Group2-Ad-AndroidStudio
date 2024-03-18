            package com.example.jobshunt.LoginRegisterActivity

            import android.content.Intent
            import android.os.Bundle
            import android.widget.Button
            import android.widget.EditText
            import android.widget.ImageView
            import android.widget.TextView
            import android.widget.Toast
            import androidx.appcompat.app.AppCompatActivity
            import com.example.jobshunt.IntroPageActivities.IntroActivity
            import com.example.jobshunt.MainActivity.MainActivity
            import com.example.jobshunt.R
            import com.google.firebase.auth.FirebaseAuth

            class LoginActivity : AppCompatActivity() {

                private lateinit var auth: FirebaseAuth

                private lateinit var emailEditText: EditText
                private lateinit var passwordEditText: EditText
                private lateinit var signInButton: Button
                private lateinit var signUpTextView: TextView
                private lateinit var backImageView: ImageView

                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.activity_login)



                    auth = FirebaseAuth.getInstance()

                    emailEditText = findViewById(R.id.email)
                    passwordEditText = findViewById(R.id.password)
                    signInButton = findViewById(R.id.signin2)
                    signUpTextView = findViewById(R.id.signup)


                    signUpTextView.setOnClickListener {
                        startActivity(Intent(this, RegistrationActivity::class.java))
                    }

                    signInButton.setOnClickListener {
                        loginUser()

                    }
                }

                private fun loginUser() {
                    val userEmail = emailEditText.text.toString()
                    val userPassword = passwordEditText.text.toString()

                    if (userEmail.isEmpty()) {
                        emailEditText.error = "Email is required!"
                        return
                    }

                    if (userPassword.isEmpty()) {
                        passwordEditText.error = "Password is required!"
                        return
                    }

                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                // Handle login failure
                                Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
                }


