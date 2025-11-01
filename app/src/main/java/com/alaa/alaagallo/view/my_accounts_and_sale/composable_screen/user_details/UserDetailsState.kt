package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.alaa.domain.entity.Invoice
import com.google.gson.annotations.SerializedName
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Immutable
data class UserDetailsState(
    val isLoading: Boolean = false,
    val isLoadingAddOperation: Boolean = false,
    val isCreditor: Boolean = true,
    val error: String = "",
    val amount: String = "",
    val additionalNotes: String = "",
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
    val image: File? = null,
    val imageUri: Uri? = null,
    val userName: String = "",
    val userPhone: String = "",
    val sort: String = "asc",
    val clientId: Int = -1,
    val totalInvoices: Int = 0,
    val totalOfCreditorInvoices: Double = 0.0,
    val totalOfDebtorInvoices: Double= 0.0,
    val totalAmount: Double = 0.0,
    val invoices: List<Invoice> = emptyList()
) {
    val visibilityAddOperationButton = amount.isNotEmpty() && image != null && additionalNotes.isNotEmpty()
}