        package com.example.jobshunt.CandidateActivities
        import android.os.Bundle
        import android.text.Editable
        import android.text.TextWatcher
        import android.widget.EditText
        import android.widget.ImageView
        import androidx.appcompat.app.AppCompatActivity
        import androidx.recyclerview.widget.LinearLayoutManager
        import androidx.recyclerview.widget.RecyclerView
        import com.example.jobshunt.R
        import com.google.firebase.database.*

        class CandidateActivity : AppCompatActivity() {

            private lateinit var recyclerView: RecyclerView
            private lateinit var candidateAdapter: CandidateAdapter
            private lateinit var databaseReference: DatabaseReference
            private lateinit var searchEditText: EditText


            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_candidate)
                recyclerView = findViewById(R.id.RecyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this)
                searchEditText = findViewById(R.id.editTextText)

                val backButton: ImageView = findViewById(R.id.backButtton)

                backButton.setOnClickListener {
                    // Handle the back button click
                    onBackPressed()
                }



                // Initialize Firebase
                databaseReference = FirebaseDatabase.getInstance().getReference("candidates")

                // Fetch data from Firebase and set it to the RecyclerView
                fetchDataFromFirebase()

                // Set up search functionality
                setUpSearch()
            }

            private fun setUpSearch() {
                searchEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // Not used
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        candidateAdapter.filter.filter(s)
                    }

                    override fun afterTextChanged(s: Editable?) {
                        // Not used
                    }
                })
            }

            private fun fetchDataFromFirebase() {
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val candidatesList = mutableListOf<Candidate>()

                        for (snapshot in dataSnapshot.children) {
                            val candidateId = snapshot.key
                            val name = snapshot.child("name").getValue(String::class.java)
                            val title = snapshot.child("title").getValue(String::class.java)
                            val description = snapshot.child("description").getValue(String::class.java)
                            val photoUrl = snapshot.child("photoUrl").getValue(String::class.java)
                            val connected = snapshot.child("connected").getValue(Boolean::class.java)

                            // Create a Candidate object
                            val candidate = Candidate(candidateId ?: "", name ?: "", title ?: "", description ?: "", photoUrl ?: "", connected ?: false)

                            candidatesList.add(candidate)
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

