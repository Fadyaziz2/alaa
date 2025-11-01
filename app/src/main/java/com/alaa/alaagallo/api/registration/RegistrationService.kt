package com.alaa.alaagallo.api.registration

import com.alaa.alaagallo.model.registration.Profile
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistrationService {
    @Headers("Accept: application/json")
    @POST("login")
    fun login(
        @Query("phone") email: String,
        @Query("password") password: String
    ): Call<Profile>
}