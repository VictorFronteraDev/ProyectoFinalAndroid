package com.example.proyectofinalandroid.connection


import com.example.proyectofinalandroid.model.Color
import com.example.proyectofinalandroid.model.DayOfWeek
import com.example.proyectofinalandroid.model.Numbers
import retrofit2.Call
import retrofit2.http.*

interface Api {
    //Get Colors from json
    @GET("colors")
    fun getColors(): Call<ArrayList<Color>>

    //Add a new color
    @FormUrlEncoded
    @POST("colors")
    fun saveColor(@Field("spanish_word") spanishWord: String, @Field("english_word") englishWord: String): Call<Color>

    // Update the color with the id selected
    @PUT("colors/{id}")
    fun updateColor(@Path("id") id: Int, @Body color: Color): Call<Color>

    // Delete the color with the id selected
    @DELETE("colors/{id}")
    fun deleteColor(@Path("id") id: Int): Call<Color>


    //Get Days of week from json
    @GET("days_of_week")
    fun getDaysOfWeek(): Call<ArrayList<DayOfWeek>>

    //Add a new day of week
    @FormUrlEncoded
    @POST("days_of_week")
    fun saveDaysOfWeek(@Field("spanish_word") spanishWord: String, @Field("english_word") englishWord: String): Call<DayOfWeek>

    // Update the day of week with the id selected
    @PUT("days_of_week/{id}")
    fun updateDaysOfWeek(@Path("id") id: Int, @Body dayOfWeek: DayOfWeek): Call<DayOfWeek>

    // Delete the day of week with the id selected
    @DELETE("days_of_week/{id}")
    fun deleteDaysOfWeek(@Path("id") id: Int): Call<Color>


    // Get Numbers from json
    @GET("numbers")
    fun getNumbers(): Call<ArrayList<Numbers>>

    // Add a new number
    @FormUrlEncoded
    @POST("numbers")
    fun saveNumber(@Field("spanish_word") spanishWord: String, @Field("english_word") englishWord: String): Call<Numbers>

    // Update the number with the id selected
    @PUT("numbers/{id}")
    fun updateNumber(@Path("id") id: Int, @Body number: Numbers): Call<Numbers>

    // Delete the number with the id selected
    @DELETE("numbers/{id}")
    fun deleteNumber(@Path("id") id: Int): Call<Numbers>

}