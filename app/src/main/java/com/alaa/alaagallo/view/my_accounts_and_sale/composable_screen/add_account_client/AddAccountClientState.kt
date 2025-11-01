package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client

import androidx.compose.runtime.Immutable
import com.alaa.domain.entity.Category


@Immutable
data class AddAccountClientState(
    val isLoading: Boolean = false,
    val isLoadingAddClient: Boolean = false,
    val error: String = "",
    val selectedCategoryName: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryPosition: Int = -1
) {
    val visibilityAddClientButton =
        name.isNotEmpty() && phoneNumber.isNotEmpty() && selectedCategoryName.isNotEmpty()
}