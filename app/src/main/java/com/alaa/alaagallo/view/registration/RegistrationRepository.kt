package com.alaa.alaagallo.view.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alaa.Constants
import com.alaa.alaagallo.api.registration.RegistrationFactory
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.model.registration.Profile
import com.alaa.alaagallo.view.home.Home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegistrationRepository {
    fun login(
        email:String,
        password: String
    ): LiveData<Resource<String>> {
        val data = MutableLiveData<Resource<String>>()
        data.value = Resource.loading()

        RegistrationFactory.login(
            email, password
        ).enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    Home.token = response.body()?.token!!
                    Home.id = response.body()?.user!!.id
                    Constants.TOKEN = response.body()?.token!!
                    Home.name = response.body()?.user!!.name
                    Home.expirationDate = response.body()?.user!!.subscription.end
                    data.postValue(Resource.success(response.message()))
                }else{
                    data.postValue(Resource.error(response.message()))
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {

                data.postValue(Resource.error(t.message))
            }

        })

        return data
    }
}