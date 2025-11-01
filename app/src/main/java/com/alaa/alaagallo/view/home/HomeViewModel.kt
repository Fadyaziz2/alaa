package com.alaa.alaagallo.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaa.alaagallo.base.helpers.Resource
import com.alaa.alaagallo.model.base.BaseResponse
import com.alaa.alaagallo.model.logout.LogOutResponse
import com.alaa.alaagallo.view.features.suggestPair.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HomeViewModel : ViewModel() {
    private val _checkDataState = MutableSharedFlow<Boolean>()
    val checkDataState: SharedFlow<Boolean> = _checkDataState.asSharedFlow()
    private val _checkDataBanned = MutableSharedFlow<Int>()
    val checkDataBanned: SharedFlow<Int> = _checkDataBanned.asSharedFlow()
    private val _errorState = MutableSharedFlow<String>()
    val errorState: SharedFlow<String> = _errorState.asSharedFlow()

    fun logOut(token: String): LiveData<Resource<LogOutResponse>> {
        return CategoriesRepository.logOut(
            token
        )
    }

    fun isUserExpired(token: String): LiveData<Resource<BaseResponse>> {
        return CategoriesRepository.isUserExpired(
            token
        )
    }

    suspend fun checkUserData(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = CategoriesRepository.checkUserData(token)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _checkDataState.emit(response.body()!!.success)
                        _checkDataBanned.emit(response.body()!!.banned)
                        if(!response.body()!!.success)
                            _errorState.emit("تم تسجيل الدخول فى جهاز اخر")
                    }
                }
                else {
                    if(response.code() == 400 || response.code() == 401 || response.code() == 402){
                        _checkDataState.emit(false)
                        _errorState.emit("تم تسجيل الدخول فى جهاز اخر")
                    }
                }
            } catch (e: HttpException) {
                _errorState.emit("لا يوجد انترنت")
            } catch (e: UnknownHostException) {
                _errorState.emit("لا يوجد انترنت")
            } catch (e: SocketException) {
                _errorState.emit("لا يوجد انترنت")
            }catch (e: SocketTimeoutException) {
                _errorState.emit("لا يوجد انترنت")
            }catch (e: Exception) {
                _errorState.emit(e.message.toString())
            }
        }
    }
}