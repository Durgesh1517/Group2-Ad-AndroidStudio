        package com.example.jobshunt.MainActivity

        import android.content.Intent
        import android.os.Bundle
        import android.widget.Button
        import androidx.appcompat.app.AppCompatActivity
        import androidx.recyclerview.widget.LinearLayoutManager
        import androidx.recyclerview.widget.RecyclerView
        import com.example.jobshunt.CandidateActivities.Candidate
        import com.example.jobshunt.CandidateActivities.CandidateActivity
        import com.example.jobshunt.CandidateActivities.CandidateAdapter
        import com.example.jobshunt.R
        import com.google.firebase.database.*

        class MainActivity : AppCompatActivity() {

            private lateinit var recyclerView: RecyclerView
            private lateinit var candidateAdapter: CandidateAdapter
            private lateinit var databaseReference: DatabaseReference
            private lateinit var exploreButton: Button

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                recyclerView = findViewById(R.id.mainRecyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this)

                // Explore button
                exploreButton = findViewById(R.id.exploreButton)

                // Set click listener for the exploreButton
                exploreButton.setOnClickListener {

                    // Logic navigate to another activity
                    startActivity(Intent(this, CandidateActivity::class.java))
                }

                // Initialize Firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("candidates")

                // Fetch data from Firebase and set it to the RecyclerView
                fetchDataFromFirebase()
            }

            private fun fetchDataFromFirebase() {
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val candidatesList = mutableListOf<Candidate>()

                        for (snapshot in dataSnapshot.children) {
                            val candidate = snapshot.getValue(Candidate::class.java)
                            if (candidate != null && candidate.connected) {
                                candidatesList.add(candidate)
                            }
                        }

                        // Initialize and set the adapter
                        candidateAdapter = CandidateAdapter(candidatesList)
                        recyclerView.adapter = candidateAdapter
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                    }
                })
            }
        }
