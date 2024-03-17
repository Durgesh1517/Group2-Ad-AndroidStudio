    package com.example.jobshunt.LoginRegisterActivity
    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.example.jobshunt.R
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.FirebaseDatabase

    class RegistrationActivity : AppCompatActivity() {

        private lateinit var nameEditText: EditText
        private lateinit var emailEditText: EditText
        private lateinit var passwordEditText: EditText
        private lateinit var signupButton: Button
        private lateinit var signinTextView: TextView
        private lateinit var auth: FirebaseAuth
        private lateinit var database: FirebaseDatabase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registration)

            auth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance()

            nameEditText = findViewById(R.id.name)
            emailEditText = findViewById(R.id.email)
            passwordEditText = findViewById(R.id.password)
            signupButton = findViewById(R.id.signin2)
            signinTextView = findViewById(R.id.signin)

            signupButton.setOnClickListener {
                createUser()
            }
            //Logic of Intent from one page to another
            signinTextView.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        private fun createUser() {
            val userName = nameEditText.text.toString()
            val userEmail = emailEditText.text.toString()
            val userPassword = passwordEditText.text.toString()
            // Validations For the gmail and Password
            if (userName.isEmpty()) {
                showToast("Name is empty!")
                return
            }
            if (userEmail.isEmpty()) {
                showToast("Email is empty!")
                return
            }
            if (userPassword.isEmpty()) {
                showToast("Password is empty!")
                return
            }
            if (userPassword.length < 6) {
                showToast("Password should be greater than 6 characters")
                return
            }
            // Logics for the Registration
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userModel = UserModel(userName, userEmail, userPassword)
                        val id = task.result?.user?.uid
                        database.reference.child("users").child(id!!).setValue(userModel)

                        showToast("Registration successful")
                    } else {
                        showToast("Error: ${task.exception?.message}")
                    }
                }
        }
        // Toast handling
        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

