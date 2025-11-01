package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.alaa.domain.entity.Invoice
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Immutable
data class EditAccountInvoiceState(
    val isLoading: Boolean = false,
    val isLoadingAddOperation: Boolean = false,
    val baseIsCreditor: Boolean = true,
    val isCreditor: Boolean = true,
    val error: String = "",
    val baseAmount: String = "",
    val amount: String = "",
    val baseAdditionalNotes: String = "",
    val additionalNotes: String = "",
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
    val image: File? = null,
    val imageUri: Uri? = null,
    val imageUrl: String? = null,
    val userName: String = "",
    val accountCustomerId: Int = -1,
    val userPhone: String = "",
    val invoiceId: Int = -1,
    val clientId: Int = -1,
) {
    val visibilityAddOperationButton = amount.isNotEmpty() && additionalNotes.isNotEmpty() && (
            baseIsCreditor != isCreditor || baseAmount != amount || baseAdditionalNotes != additionalNotes
            )
}