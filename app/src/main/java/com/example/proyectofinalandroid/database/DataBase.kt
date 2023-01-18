package com.example.proyectofinalandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinalandroid.dao.FavouriteDao
import com.example.proyectofinalandroid.model.Favourite
import java.util.concurrent.Executors

@Database(
    entities = [Favourite::class],
    version = 1,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    companion object {

        //Make sure we are reading and writing from RAM not from cache
        @Volatile
        private var INSTANCE : DataBase? = null

        private const val NUM_THREADS = 2

        //Create the ExecutorService with the threads defined before
        val databaseWriterExecutor = Executors.newFixedThreadPool(NUM_THREADS)

        //Method to create/access to the db (favourite_database)
        fun getData(context: Context) : DataBase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                    DataBase::class.java, "favourite_database")
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}