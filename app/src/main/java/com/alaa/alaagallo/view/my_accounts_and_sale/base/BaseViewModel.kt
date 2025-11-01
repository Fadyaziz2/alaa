package com.alaa.alaagallo.view.my_accounts_and_sale.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaa.domain.exceptions.NoInternetException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) :
    ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<E?>()
    val effect: SharedFlow<E?> = _effect.asSharedFlow()
    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorUiState, String) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        runWithErrorCheck(
            onError = onError,
            dispatcher = dispatcher
        ) {
            onSuccess(function())
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    private fun runWithErrorCheck(
        onError: (ErrorUiState, String) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        function: suspend () -> Unit,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                function()
            } catch (e: NoInternetException) {
//                onError(ErrorUiState.NoInternet(resources.getString(R.string.no_internet)),resources.getString(R.string.no_internet))
                onError(ErrorUiState.NoInternet("لا يوجد انترنت"),"لا يوجد انترنت")
            } catch (e: Exception) {
                onError(ErrorUiState.Server(e.message.toString()),e.message.toString())
            }
        }
    }

    protected fun sendNewEffect(newEffect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
        }
    }
}