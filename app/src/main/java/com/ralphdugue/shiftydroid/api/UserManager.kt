package com.ralphdugue.shiftydroid.api

import com.ralphdugue.shiftydroid.models.User
import com.ralphdugue.shiftydroid.services.UserService
import io.reactivex.Observable

/**
 * Created by Ralph on 5/27/2017.
 */

class UserManager(private val shiftyAPI: ShiftyAPI) {
    private val service: UserService

    init {
        service = shiftyAPI.provideUserService()
    }

    fun handleRegisterData(user: User): Observable<UserResponses> {
        return Observable.create {
            subscriber ->
            val response = service.registerUser(user).execute()
            if (response.isSuccessful) {
                val userResponse = response.body()
                subscriber.onNext(userResponse)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun handleLoginData(user: User): Observable<UserResponses> {
        return Observable.create {
            subscriber ->
            val response = service.login(user).execute()
            if (response.isSuccessful) {
                val userResponse = response.body()
                subscriber.onNext(userResponse)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}