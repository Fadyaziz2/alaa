package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.barcode

import com.alaa.alaagallo.R
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.usecase.ManageProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarcodePrinterViewModel @Inject constructor(
    private val manageProductUseCase: ManageProductUseCase,
) : BaseViewModel<BarcodePrinterUiState, BarcodePrinterUiEffect>(BarcodePrinterUiState()) {

    init {
        fetchProducts()
    }

    fun refreshProducts() {
        fetchProducts()
    }

    private fun fetchProducts() {
        updateState { it.copy(isLoading = true, errorState = null) }
        tryToExecute(
            function = { manageProductUseCase.getAllProduct() },
            onSuccess = { products ->
                val previousId = state.value.selectedProduct?.id
                val newSelection = products.firstOrNull { it.id == previousId } ?: products.firstOrNull()
                updateState {
                    it.copy(
                        isLoading = false,
                        products = products,
                        selectedProduct = newSelection,
                        errorState = null,
                    )
                }
            },
            onError = ::handleError,
        )
    }

    private fun handleError(errorState: ErrorUiState, message: String) {
        updateState { it.copy(isLoading = false, errorState = errorState) }
        if (message.isBlank()) {
            sendNewEffect(BarcodePrinterUiEffect.ShowToastRes(R.string.barcode_products_error))
        } else {
            sendNewEffect(BarcodePrinterUiEffect.ShowToastMessage(message))
        }
    }

    fun onSelectProduct(product: ProductEntity) {
        updateState { it.copy(selectedProduct = product) }
    }

    fun onSelectBarcodeType(type: BarcodeType) {
        updateState { it.copy(barcodeType = type) }
    }

    fun onQuantityChanged(value: String) {
        if (value.isEmpty() || value.all { it.isDigit() }) {
            updateState { it.copy(quantity = value) }
        }
    }

    fun onPrintClick() {
        val product = state.value.selectedProduct ?: run {
            sendNewEffect(BarcodePrinterUiEffect.ShowToastRes(R.string.barcode_error_select_product))
            return
        }
        if (product.barcode.isBlank()) {
            sendNewEffect(BarcodePrinterUiEffect.ShowToastRes(R.string.barcode_error_missing_code))
            return
        }
        val copies = state.value.quantity.toIntOrNull()
        if (copies == null || copies <= 0) {
            sendNewEffect(BarcodePrinterUiEffect.ShowToastRes(R.string.barcode_error_quantity))
            return
        }
        if (copies > MAX_ALLOWED_COPIES) {
            sendNewEffect(
                BarcodePrinterUiEffect.ShowToastRes(
                    messageRes = R.string.barcode_error_max_copies,
                    formatArgs = listOf(MAX_ALLOWED_COPIES),
                )
            )
            return
        }
        sendNewEffect(BarcodePrinterUiEffect.Print(product, state.value.barcodeType, copies))
    }

    companion object {
        private const val MAX_ALLOWED_COPIES = 50
    }
}
