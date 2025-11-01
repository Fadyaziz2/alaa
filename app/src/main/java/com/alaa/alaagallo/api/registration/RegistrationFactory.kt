package com.alaa.alaagallo.api.registration

import com.alaa.alaagallo.api.ServiceBuilder
import com.alaa.alaagallo.model.registration.Profile
import retrofit2.Call

object RegistrationFactory {
    fun login(
        email: String,
        password: String
    ): Call<Profile> {
        val loginService = ServiceBuilder.buildService(RegistrationService::class.java)
        return loginService.login(
            email, password
        )
    }
}