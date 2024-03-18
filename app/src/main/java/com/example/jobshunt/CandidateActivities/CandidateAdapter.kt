        package com.example.jobshunt.CandidateActivities

        import android.content.Context
        import android.content.Intent
        import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
        import android.widget.Filter
        import android.widget.Filterable
        import android.widget.ImageView
        import android.widget.TextView
        import android.widget.Toast
        import androidx.recyclerview.widget.RecyclerView
        import com.example.jobshunt.MainActivity.MainActivity
        import com.example.jobshunt.R
        import com.squareup.picasso.Picasso

        class CandidateAdapter(private val candidates: List<Candidate>) :
            RecyclerView.Adapter<CandidateAdapter.ViewHolder>(), Filterable {

            private var candidatesFiltered: List<Candidate> = candidates

            interface OnItemClickListener {
                fun onItemClick(candidate: Candidate)
            }

            private var itemClickListener: OnItemClickListener? = null

            fun setOnItemClickListener(listener: OnItemClickListener) {
                this.itemClickListener = listener
            }

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val photoImageView: ImageView = itemView.findViewById(R.id.candidatePhotoImageView)
                val nameTextView: TextView = itemView.findViewById(R.id.candidateNameTextView)
                val titleTextView: TextView = itemView.findViewById(R.id.candidateTitleTextView)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val layoutResId = if (parent.context is MainActivity) {
                    R.layout.main_activity_candidate_item
                } else {
                    R.layout.candidatelist
                }
                val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val candidate = candidatesFiltered[position]

                if (!candidate.photoUrl.isNullOrEmpty()) {
                    Picasso.get().load(candidate.photoUrl).into(holder.photoImageView)
                } else {
                    holder.photoImageView.setImageResource(R.drawable.syed)
                }

                holder.nameTextView.text = candidate.name
                holder.titleTextView.text = candidate.title

                // Set click listener
                holder.itemView.setOnClickListener {
                    // Check if the candidate is already connected
                    if (candidate.connected) {
                        // Display a toast message indicating that the candidate is already connected
                        showToast(holder.itemView.context, "Candidate is already connected")
                    } else {
                        // Start the CandidateDetailActivity
                        val intent = Intent(holder.itemView.context, CandidateDetailActivity::class.java)
                        // Pass the selected candidate to the detail activity
                        intent.putExtra("selectedCandidate", candidate)
                        holder.itemView.context.startActivity(intent)
                    }
                }
            }

            override fun getItemCount(): Int {
                return candidatesFiltered.size
            }

            // Function to display toast messages
            private fun showToast(context: Context, message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            override fun getFilter(): Filter {
                return object : Filter() {
                    override fun performFiltering(constraint: CharSequence?): FilterResults {
                        val filteredList = mutableListOf<Candidate>()
                        if (constraint.isNullOrEmpty()) {
                            filteredList.addAll(candidates)
                        } else {
                            val filterPattern = constraint.toString().trim().toLowerCase()
                            for (candidate in candidates) {
                                if (candidate.name.toLowerCase().contains(filterPattern) ||
                                    candidate.title.toLowerCase().contains(filterPattern)
                                ) {
                                    filteredList.add(candidate)
                                }
                            }
                        }
                        val results = FilterResults()
                        results.values = filteredList
                        return results
                    }

                    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                        candidatesFiltered = results?.values as List<Candidate>
                        notifyDataSetChanged()
                    }
                }
            }
        }
