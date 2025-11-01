package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import androidx.compose.runtime.Immutable
import com.alaa.domain.entity.AccountCustomer
import com.alaa.domain.entity.Invoice
import com.alaa.domain.entity.Category


@Immutable
data class AccountsState(
    val isLoading: Boolean = false,
    val isFirst: Boolean = true,
    val isLoadingAddCategory: Boolean = false,
    val isLoadingDeleteClient: Boolean = false,
    val isLoadingDeleteCategory: Boolean = false,
    val isLoadingUpdateCategory: Boolean = false,
    val isSucceedAddCategory: Boolean = false,
    val isSucceedDeleteClient: Boolean = false,
    val isSucceedDeleteCategory: Boolean = false,
    val isSucceedUpdateCategory: Boolean = false,
    val isSelectedAll: Boolean = true,
    val error: String = "",
    val selectedCategoryPos: Int = 0,
    val selectedCategoryName: String = "الكل",
    val totalReceivables: String = "0",
    val totalDept: String = "0",
    val totalShowingReceivables: String = "0",
    val totalShowingDept: String = "0",
    val categories: List<Category> = emptyList(),
    val allUsers: List<User> = emptyList(),
    val users: List<User> = emptyList(),
    val categoriesUi: List<CategoryUi> = emptyList()
)

@Immutable
data class User(
    val id: Int,
    val name: String,
    val status: String,
    val mobile: String,
    val categoryName: String,
    val categoryId: Int,
    val accountInvoicesCount: Double,
    val accountTotalInvoice: Double,
    val invoices: List<Invoice>
)

@Immutable
data class CategoryUi(
    val id: Int,
    val name: String,
    val numOfAccounts: Int,
    val isSelected: Boolean = false
)

fun AccountCustomer.toUser(categoryName: String, categoryId: Int) = User(
    id = id,
    name = name,
    status = status,
    mobile = mobile,
    categoryName = categoryName,
    categoryId = categoryId,
    accountTotalInvoice = accountTotalInvoice ?: 0.0,
    accountInvoicesCount = accountInvoicesCount ?: 0.0,
    invoices = invoices
)