        package com.example.jobshunt.CandidateActivities


        import android.content.Intent
        import android.os.Bundle
        import android.widget.Button
        import android.widget.ImageView
        import android.widget.TextView
        import android.widget.Toast
        import androidx.appcompat.app.AppCompatActivity
        import com.example.jobshunt.MainActivity.MainActivity
        import com.example.jobshunt.R
        import com.google.firebase.database.FirebaseDatabase
        import com.squareup.picasso.Picasso

        class CandidateDetailActivity : AppCompatActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_candidate_detail)



                val selectedCandidate = intent.getParcelableExtra<Candidate>("selectedCandidate")

                val nameTextView: TextView = findViewById(R.id.detailed_name)
                val titleTextView: TextView = findViewById(R.id.detailed_title)
                val descriptionTextView: TextView = findViewById(R.id.detailed_desc)
                val photoImageView: ImageView = findViewById(R.id.detailed_img)
                val connectButton: Button = findViewById(R.id.add_to_cart)


                nameTextView.text = selectedCandidate?.name
                titleTextView.text = selectedCandidate?.title
                descriptionTextView.text = selectedCandidate?.description

                // Load the candidate's photo using Picasso
                if (!selectedCandidate?.photoUrl.isNullOrEmpty()) {
                    Picasso.get().load(selectedCandidate?.photoUrl).into(photoImageView)
                } else {
                    // Handle the case when the photo URL is empty
                }

                // Set click listener for the Connect button
                connectButton.setOnClickListener {
                    // Update connected status in Firebase Realtime Database
                    updateConnectedStatus(selectedCandidate?.candidateId)
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }

            private fun updateConnectedStatus(candidateId: String?) {
                candidateId?.let {
                    val databaseReference = FirebaseDatabase.getInstance().getReference("candidates/$candidateId")
                    databaseReference.child("connected").setValue(true)
                        .addOnSuccessListener {
                            // Update successful
                            Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            // Handle failure
                            Toast.makeText(this, "Failed to connect!", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }
