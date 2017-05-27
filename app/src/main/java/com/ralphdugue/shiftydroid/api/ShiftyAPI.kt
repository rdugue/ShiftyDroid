package com.ralphdugue.shiftydroid.api

/**
 * Created by Ralph on 5/26/2017.
 */
import com.ralphdugue.shiftydroid.services.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ShiftyAPI() {
    val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    fun provideUserService(): UserService = retrofit.create(UserService::class.java)
}