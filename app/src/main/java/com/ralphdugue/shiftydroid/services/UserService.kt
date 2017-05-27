package com.ralphdugue.shiftydroid.services

import com.ralphdugue.shiftydroid.api.UserResponses
import com.ralphdugue.shiftydroid.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Ralph on 5/26/2017.
 */

interface UserService {
    @POST("/register/user")
    fun registerUser(@Body user: User): Call<UserResponses>;

    @POST("/login")
    fun login(@Body user: User): Call<UserResponses>;
}