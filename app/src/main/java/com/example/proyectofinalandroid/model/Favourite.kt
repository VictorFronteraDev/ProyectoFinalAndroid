package com.example.proyectofinalandroid.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourite (@PrimaryKey var id: Int?, var spanishWord: String, var englishWord: String) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(spanishWord)
        parcel.writeString(englishWord)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favourite> {
        override fun createFromParcel(parcel: Parcel): Favourite {
            return Favourite(parcel)
        }

        override fun newArray(size: Int): Array<Favourite?> {
            return arrayOfNulls(size)
        }
    }
}