package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice

sealed interface EditAccountInvoiceEffect {
    data class ShowToastError(val message:String): EditAccountInvoiceEffect
    data object SucceedEditAccountInvoice: EditAccountInvoiceEffect
    data object SucceedDeleteAccountInvoice: EditAccountInvoiceEffect
}