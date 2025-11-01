package com.alaa.alaagallo.view.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alaa.alaagallo.base.helpers.Resource

class RegistrationViewModel: ViewModel() {
    fun login(email: String, password: String) : LiveData<Resource<String>> {
        return RegistrationRepository.login(
            email, password
        )
    }
}