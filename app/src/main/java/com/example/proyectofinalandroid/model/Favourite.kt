package com.example.proyectofinalandroid.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class Favourite(@ColumnInfo(name = "spanishWord") var spanishWord: String?,
                      @ColumnInfo (name = "englishWord") var englishWord: String?) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0

}