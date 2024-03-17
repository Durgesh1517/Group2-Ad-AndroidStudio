    package com.example.jobshunt.CandidateActivities
    import android.os.Parcel
    import android.os.Parcelable
    // All the candidates fields that need
    data class Candidate(
        var candidateId: String = "",
        val name: String = "",
        val title: String = "",
        val description: String = "",
        val photoUrl: String = "",
        var connected: Boolean = false, // Change to var to allow modification
        val connections: Map<String, Boolean> = emptyMap()
    ) : Parcelable {


        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readByte() != 0.toByte(),
            readMap(parcel)
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(candidateId)
            parcel.writeString(name)
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeString(photoUrl)
            parcel.writeByte(if (connected) 1 else 0)
            writeMap(parcel, connections)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Candidate> {
            override fun createFromParcel(parcel: Parcel): Candidate {
                return Candidate(parcel)
            }

            override fun newArray(size: Int): Array<Candidate?> {
                return arrayOfNulls(size)
            }

            private fun writeMap(parcel: Parcel, map: Map<String, Boolean>) {
                parcel.writeInt(map.size)
                for ((key, value) in map) {
                    parcel.writeString(key)
                    parcel.writeInt(if (value) 1 else 0)
                }
            }

            private fun readMap(parcel: Parcel): Map<String, Boolean> {
                val size = parcel.readInt()
                val map = mutableMapOf<String, Boolean>()
                repeat(size) {
                    val key = parcel.readString() ?: ""
                    val value = parcel.readInt() == 1
                    map[key] = value
                }
                return map
            }


        }
    }

