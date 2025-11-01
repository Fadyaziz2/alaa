package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client

import androidx.compose.runtime.Immutable
import com.alaa.domain.entity.Category


@Immutable
data class EditAccountClientState(
    val isLoading: Boolean = false,
    val isLoadingEditClient: Boolean = false,
    val error: String = "",
    val clientId: Int = -1,
    val selectedCategoryName: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val baseSelectedCategoryName: String = "",
    val baseName: String = "",
    val basePhoneNumber: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryPosition: Int = -1,
    val baseCategoryId: Int = -1
) {
    val visibilityEditClientButton =
        name.isNotEmpty() && phoneNumber.isNotEmpty() &&
                selectedCategoryName.isNotEmpty() && (name != baseName ||
                phoneNumber != basePhoneNumber || selectedCategoryName != baseSelectedCategoryName)
}