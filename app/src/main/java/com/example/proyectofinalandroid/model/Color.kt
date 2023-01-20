package com.example.proyectofinalandroid.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Color(@SerializedName("id") @Expose var id: Int?,
                 @SerializedName("spanish_word") @Expose var spanishWord: String,
                 @SerializedName("english_word") @Expose var englishWord: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        (parcel.readValue(Int::class.java.classLoader) as? Int),
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

    companion object CREATOR : Parcelable.Creator<Color> {
        override fun createFromParcel(parcel: Parcel): Color {
            return Color(parcel)
        }

        override fun newArray(size: Int): Array<Color?> {
            return arrayOfNulls(size)
        }
    }

}
