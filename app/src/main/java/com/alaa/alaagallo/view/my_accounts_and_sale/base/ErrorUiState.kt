package com.alaa.alaagallo.view.my_accounts_and_sale.base

sealed interface ErrorUiState {
    data class NoInternet(val message: String) : ErrorUiState
    data class Server(val message: String) : ErrorUiState
}