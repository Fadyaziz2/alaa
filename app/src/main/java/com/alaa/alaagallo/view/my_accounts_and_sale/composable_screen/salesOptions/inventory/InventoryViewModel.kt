package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.inventory

import android.util.Log
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.NewItemValues
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.Customer
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.sales.SalesUiEffect
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.usecase.ManageProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val useCase: ManageProductUseCase,
) :
    BaseViewModel<InventoryUiState, InventoryUiEffect>(InventoryUiState()) {

    init {
        getAllInventoryProduct()
    }

    private fun getAllInventoryProduct() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                val invItems = useCase.getAllProduct()
                val orderLimitItem = useCase.getAllProductLimit()
                val soldOutItems = useCase.getAllOutOfStock()
                val returnItems = useCase.getAllRefunds()
                ProductItemsTypes(
                    inventoryItems = invItems,
                    orderLimitItem = orderLimitItem.products,
                    soldOutItems = soldOutItems.products,
                    returnItems = returnItems
                )
            },
            onSuccess = { productItemsTypes ->
                updateState {
                    it.copy(
                        isLoading = false,
                        productItemsTypes = productItemsTypes
                    )
                }
            },
            onError = ::onError
        )
    }

    fun onClickExcel(){}
    fun onClickPdf(){}
    fun onClickItem(product: ProductEntity, isReturn: Boolean = false, returnId: Int = -1) {
        updateState {
            it.copy(
                returnItemId = returnId,
                editValues = product,
                newItemValues = NewItemValues(
                    code = product.barcode,
                    name = product.name,
                    qty = product.stock.toString(),
                    priceForBuy = product.buyPrice.toString(),
                    priceForSale = product.sellingPrice.toString(),
                    orderLimit = product.minimumStock.toString()
                ),
            )
        }
        if (isReturn)
            updateState {
                it.copy(
                    isEdit = true,
                    isAddingReturn = true
                )
            }
        else updateState {
            it.copy(
                isEdit = true,
                isAddingNew = true
            )
        }

    }

    private fun onEditReturn() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                useCase.updateProductRefund(
                    id = state.value.returnItemId.toString(),
                    productId = state.value.editValues?.id ?: "0",
                    quantity = state.value.newItemValues.qty.toLongOrNull() ?: 0L
                )
            },
            onSuccess = {
                sendNewEffect(InventoryUiEffect.ShowToastSuccessEditingCustomer)
                reset()
                getAllInventoryProduct()
            },
            onError = ::onError
        )
    }

    private fun onAddReturn() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                useCase.addProductRefund(
                    productId = state.value.newItemValues.id,
                    quantity = state.value.newItemValues.qty.toLongOrNull() ?: 0L,
                )
            },
            onSuccess = {
                sendNewEffect(InventoryUiEffect.ShowToastSuccessAddingCustomer)
                reset()
                getAllInventoryProduct()
            },
            onError = ::onError
        )
    }

    private fun onEditCustomer() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                useCase.updateProduct(
                    id = state.value.editValues?.id ?: "0",
                    name = state.value.newItemValues.name,
                    buyPrice = state.value.newItemValues.priceForBuy.toLongOrNull() ?: 0L,
                    sellingPrice = state.value.newItemValues.priceForSale.toLongOrNull() ?: 0L,
                    minimumStock = state.value.newItemValues.orderLimit.toLongOrNull() ?: 0L,
                    stock = state.value.newItemValues.qty.toLongOrNull() ?: 0L,
                    status = "active"
                )
            },
            onSuccess = {
                sendNewEffect(InventoryUiEffect.ShowToastSuccessEditingCustomer)
                reset()
                getAllInventoryProduct()
            },
            onError = ::onError
        )
    }

    private fun onAddCustomer() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                useCase.addProduct(
                    name = state.value.newItemValues.name,
                    buyPrice = state.value.newItemValues.priceForBuy.toLongOrNull() ?: 0L,
                    sellingPrice = state.value.newItemValues.priceForSale.toLongOrNull() ?: 0L,
                    minimumStock = state.value.newItemValues.orderLimit.toLongOrNull() ?: 0L,
                    stock = state.value.newItemValues.qty.toLongOrNull() ?: 0L,
                    status = "active"
                )
            },
            onSuccess = {
                sendNewEffect(InventoryUiEffect.ShowToastSuccessAddingCustomer)
                reset()
                getAllInventoryProduct()
            },
            onError = ::onError
        )
    }

    fun onChangeCodeInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    code = value
                )
            )
        }
    }

    fun onChangeNameInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    name = value
                )
            )
        }
    }

    fun onChangeOrderLimitInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    orderLimit = value
                )
            )
        }
    }

    fun onChangePriceForSaleInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    priceForSale = value
                )
            )
        }
    }

    fun onChangePriceForBuyInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    priceForBuy = value
                )
            )
        }
    }

    fun onChangeQtyInNewItemValue(value: String) {
        updateState {
            it.copy(
                newItemValues = it.newItemValues.copy(
                    qty = value
                )
            )
        }
    }

    private fun reset() {
        updateState {
            it.copy(
                newItemValues = NewItemValues(),
                editValues = null,
                returnItemId = null,
                isEdit = false,
                isAddingNew = false,
                isAddingReturn = false,
                isDoneLoading = false,
                isLoading = false
            )
        }
    }

    fun onChangeSearchValue(value: String) {
        updateState { it.copy(searchValue = value) }
    }

    fun navigateToAddNew() {
        reset()
        updateState { it.copy(isAddingNew = true) }
    }

    fun navigateToAddNewReturn() {
        reset()
        updateState { it.copy(isAddingReturn = true) }
    }

    fun navigateBackToDetails() {
        if (state.value.isAddingNew)
            updateState { it.copy(isAddingNew = false) }
        else if (state.value.isAddingReturn)
            updateState { it.copy(isAddingReturn = false) }
        else
            sendNewEffect(InventoryUiEffect.NavigateBack)

    }

    fun onChangeSelectedMenuItem(menuItem: MenuItems) {
        updateState { it.copy(selectedInventoryItem = menuItem, searchValue = "") }
    }

    fun onClickDone() {
        if (state.value.isEdit) {
            onEditCustomer()
        } else {
            onAddCustomer()
        }
    }

    fun onClickDoneOnReturn() {
        if (state.value.isEdit) {
            onEditReturn()
        } else {
            onAddReturn()
        }
    }

    fun searchProductWithBarcode() {
        val barcode = state.value.newItemValues.code
        val selectedProduct =
            state.value.productItemsTypes.inventoryItems.firstOrNull { it.barcode == barcode }
        if (selectedProduct == null) {
            sendNewEffect(InventoryUiEffect.ShowToastError("لا يوجد منتج بهذا الكود"))
            return
        }
        updateState {
            it.copy(
                newItemValues = NewItemValues(
                    id = selectedProduct.id,
                    code = selectedProduct.barcode,
                    name = selectedProduct.name,
                    orderLimit = selectedProduct.minimumStock.toString(),
                    priceForSale = selectedProduct.sellingPrice.toString(),
                    priceForBuy = selectedProduct.buyPrice.toString(),
                )
            )
        }
    }

    fun searchProductUsingId(id: Long) {
        val selectedProduct =
            state.value.productItemsTypes.inventoryItems.firstOrNull { it.id == id.toString() }
        if (selectedProduct == null) {
            sendNewEffect(InventoryUiEffect.ShowToastError("لا يوجد منتج بهذا الكود"))
            return
        }
        updateState {
            it.copy(
                newItemValues = NewItemValues(
                    id = selectedProduct.id,
                    code = selectedProduct.barcode,
                    name = selectedProduct.name,
                    orderLimit = selectedProduct.minimumStock.toString(),
                    priceForSale = selectedProduct.sellingPrice.toString(),
                    priceForBuy = selectedProduct.buyPrice.toString(),
                )
            )
        }
    }

    private fun onError(error: ErrorUiState, message: String) {
        Log.e("TAG", "onError: $message")
        updateState {
            it.copy(isLoading = false, isDoneLoading = false, error = message)
        }
        sendNewEffect(InventoryUiEffect.ShowToastError(message))
    }
}