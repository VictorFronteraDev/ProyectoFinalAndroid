package com.example.proyectofinalandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.proyectofinalandroid.model.Favourite
import com.example.proyectofinalandroid.repository.RepositoryFavourite

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private var repositoryFavourite: RepositoryFavourite
    private var allFavorites: LiveData<List<Favourite>>

    init {
        repositoryFavourite = RepositoryFavourite(application)

        //obtain all favorites from repository
        allFavorites = repositoryFavourite.getAllFavourites()

    }

    //Method to obtain all favorites
    fun getAllFavorites() : LiveData<List<Favourite>> {
        return allFavorites
    }

    //Method to insert a favourite through the repository
    fun add(favourite: Favourite) {
        repositoryFavourite.add(favourite)
    }

    //Method to update a favourite through the repository
    fun update(id: Int, spanishWord: String, englishWord: String) {
        repositoryFavourite.update(id, spanishWord, englishWord)
    }
}