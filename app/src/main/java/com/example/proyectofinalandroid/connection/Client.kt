package com.example.proyectofinalandroid.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object {
        const val URL: String = "https://my-json-server.typicode.com/VictorFronteraDev/dbJson/"
        var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {
            if(retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }

            return retrofit
        }
    }
}