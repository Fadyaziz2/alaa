package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.barcode

import androidx.annotation.StringRes
import com.alaa.alaagallo.R
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.domain.entity.ProductEntity

data class BarcodePrinterUiState(
    val isLoading: Boolean = false,
    val products: List<ProductEntity> = emptyList(),
    val selectedProduct: ProductEntity? = null,
    val barcodeType: BarcodeType = BarcodeType.NAME_AND_PRICE,
    val quantity: String = "1",
    val errorState: ErrorUiState? = null,
)

enum class BarcodeType(@StringRes val titleRes: Int, val showPrice: Boolean) {
    NAME_AND_PRICE(R.string.barcode_type_name_price, true),
    NAME_ONLY(R.string.barcode_type_name_only, false)
}

sealed interface BarcodePrinterUiEffect {
    data class ShowToastRes(@StringRes val messageRes: Int, val formatArgs: List<Any> = emptyList()) : BarcodePrinterUiEffect
    data class ShowToastMessage(val message: String) : BarcodePrinterUiEffect
    data class Print(val product: ProductEntity, val barcodeType: BarcodeType, val copies: Int) : BarcodePrinterUiEffect
}
