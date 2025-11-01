package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.sales

import android.util.Log
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.BuyInvoice
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.InvoiceType
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.Item
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.NewItemValues
import com.alaa.data.source.InvoiceItem
import com.alaa.domain.entity.Invoice
import com.alaa.domain.usecase.ManageInvoiceUseCase
import com.alaa.domain.usecase.ManageProductUseCase
import com.alaa.domain.usecase.ManageUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val manageUserUseCase: ManageUserUseCase,
    private val productUseCase: ManageProductUseCase,
    private val invoiceUseCase: ManageInvoiceUseCase,
) : BaseViewModel<SalesUiState, SalesUiEffect>(SalesUiState())
{

    init {
        getAllCustomer()
        getAllProducts()
    }

    private fun getAllCustomer() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageUserUseCase.getAllCustomerUsers() },
            onSuccess = { customers ->
                updateState {
                    it.copy(
                        isLoading = false, customers = customers
                    )
                }
            },
            onError = ::onError
        )
    }

    private fun getAllProducts() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(function = { productUseCase.getAllProduct() }, onSuccess = { products ->
            updateState {
                it.copy(
                    isLoading = false, products = products
                )
            }
        }, onError = ::onError
        )
    }


    private fun getAllInvoices() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(function = { invoiceUseCase.getAllInvoices() }, onSuccess = { cusResInvoices ->
            val invoices = cusResInvoices.customerInvoices
            updateState {
                it.copy(
                    isLoading = false,
                    searchInvoice = it.searchInvoice.copy(invoices = invoices.map { invoice ->
                        BuyInvoice(id = invoice.id,
                            supplierName = invoice.saleUserName,
                            total = invoice.total,
                            qty = invoice.items.sumOf { it.quantity }.toDouble(),
                            dateTime = invoice.createdAt.take(10),
                            type = InvoiceType.Amount,
                            items = invoice.items.map { item ->
                                Item(
                                    id = item.id,
                                    code = item.product,
                                    name = item.product,
                                    qty = item.quantity.toDouble(),
                                    priceForSell = item.sellingPrice.toDouble(),
                                    priceForBuy = item.buyPrice.toDouble(),
                                    price = item.sellingPrice.toDouble(),
                                    total = item.total.toDouble()
                                )
                            })
                    })
                )
            }
        }, onError = ::onError
        )
    }

    fun navigateBackToDetails() {
        if (state.value.searchInvoice.visible && state.value.searchInvoice.selectedInvoice != null && !state.value.addNewItemToPreviousInvoice){
            updateState {
                it.copy(
                    searchInvoice = it.searchInvoice.copy(
                        selectedInvoice = null
                    )
                )
            }
        }

        else if (state.value.addNewItemToPreviousInvoice) {
            updateState {
                it.copy(
                    addNewItemToPreviousInvoice = false
                )
            }
        } else if (state.value.addNewInvoiceState.visible) {
            if (state.value.addNewInvoiceState.addNewItemVisible) updateState {
                it.copy(
                    addNewInvoiceState = it.addNewInvoiceState.copy(
                        addNewItemVisible = false
                    )
                )
            }
            else updateState { it.copy(addNewInvoiceState = AddNewInvoice()) }
        } else if (state.value.searchInvoice.visible) {
            if (state.value.searchInvoice.selectedInvoice == null) updateState {
                it.copy(
                    searchInvoice = it.searchInvoice.copy(
                        visible = false
                    )
                )
            }
            else {
                updateState {
                    it.copy(
                        searchInvoice = it.searchInvoice.copy(
                            selectedInvoice = null
                        )
                    )
                }
            }
        } else sendNewEffect(SalesUiEffect.NavigateBack)
    }

    fun onNavigateToAddNewInvoice() {
        updateState { it.copy(addNewInvoiceState = AddNewInvoice(true)) }
    }

    fun onNavigateToSearchInvoice() {
        getAllInvoices()
        updateState {
            it.copy(
                searchInvoice = it.searchInvoice.copy(
                    visible = true
                )
            )
        }
    }

    fun onChangeSearchValue(value: String) {
        updateState { it.copy(searchValue = value) }
    }


    fun onChangeCodeInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        code = value
                    )
                )
            )
        }
    }

    fun onChangeNameInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        name = value
                    )
                )
            )
        }
    }

    fun onChangeOrderLimitInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        orderLimit = value
                    )
                )
            )
        }
    }

    fun onChangePriceForSaleInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        priceForSale = value
                    )
                )
            )
        }
    }

    fun onChangePriceForBuyInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        priceForBuy = value
                    )
                )
            )
        }
    }

    fun onChangeQtyInNewItemValue(value: String) {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        qty = value
                    )
                )
            )
        }
    }

    fun onClickSaveNewInvoice() {
        updateState { state ->
            state.copy(
                addNewInvoiceState = state.addNewInvoiceState.copy(
                    isSaveLoading = true
                )
            )
        }
        tryToExecute(function = {
            val items = state.value.addNewInvoiceState.items
            val supplierId = state.value.addNewInvoiceState.customer?.id
            val type =
                if (state.value.addNewInvoiceState.type == InvoiceType.Amount) "monetary" else "postponed"


            if (supplierId == null) {
                throw Exception("يجب اختيار مورد")
            }
            if (state.value.addNewInvoiceState.items.isEmpty()) {
                throw Exception("يجب اختيار منتج واحد علي الاقل")
            }

            invoiceUseCase.addInvoice(supplierId.toString(), type, items.map { item ->
                com.alaa.domain.repository.InvoiceItem(
                    item.id,
                    item.qty.toInt(),
                    item.priceForSell.toInt(),
                    item.priceForBuy.toInt()
                )
            })
        }, onSuccess = { message ->

            sendNewEffect(SalesUiEffect.ShowToastError(message))

            updateState {
                it.copy(isSuccessSaving = true , addNewInvoiceState = it.addNewInvoiceState.copy(isSaveLoading = false))
            }

        }, onError = ::onError
        )
    }

    fun onClickDoneSaving() {
        //NAVIGATE BACK
        updateState {
            it.copy(isSuccessSaving = false, addNewInvoiceState = AddNewInvoice())
        }
    }
    fun onShareSuccessDialog(){

    }

    fun searchProductWithBarcode() {
        val barcode = state.value.addNewInvoiceState.newInvoiceValues.code
        val selectedProduct = state.value.products.firstOrNull { it.barcode == barcode }
        if (selectedProduct == null) {
            sendNewEffect(SalesUiEffect.ShowToastError("لا يوجد منتج بهذا الكود"))
            return
        }
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = NewItemValues(
                        id = selectedProduct.id,
                        code = selectedProduct.barcode,
                        name = selectedProduct.name,
                        orderLimit = selectedProduct.minimumStock.toString(),
                        priceForSale = selectedProduct.sellingPrice.toString(),
                        priceForBuy = selectedProduct.buyPrice.toString(),
                    )
                )
            )
        }
    }

    fun searchProductUsingId(id: Long) {
        val selectedProduct = state.value.products.firstOrNull { it.id == id.toString() }
        if (selectedProduct == null) {
            sendNewEffect(SalesUiEffect.ShowToastError("لا يوجد منتج بهذا الكود"))
            return
        }
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = NewItemValues(
                        id = selectedProduct.id,
                        code = selectedProduct.barcode,
                        name = selectedProduct.name,
                        orderLimit = selectedProduct.minimumStock.toString(),
                        priceForSale = selectedProduct.sellingPrice.toString(),
                        priceForBuy = selectedProduct.buyPrice.toString(),
                    )
                )
            )
        }
    }

    fun onClickDone() {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        isDoneLoading = true
                    )
                )
            )
        }
        tryToExecute(function = { }, onSuccess = {
            val newItem = state.value.addNewInvoiceState.newInvoiceValues
            if (state.value.addNewInvoiceState.items.any {
                    it.id.toString() == newItem.id
                }) {
                onError(ErrorUiState.Server(""), "هذا المنتج موجود بالفعل في الفاتورة")
                return@tryToExecute
            }

            if (!state.value.products.any {
                    it.id == newItem.id
                }) {
                onError(ErrorUiState.Server(""), "برجاء اختيار منتج موجود بالفعل")
                return@tryToExecute
            }

            if (state.value.addNewInvoiceState.newInvoiceValues.qty == "") {
                onError(ErrorUiState.Server(""), "برجاء اضافة الكمية")
                return@tryToExecute
            }
            updateState {
                it.copy(
                    addNewInvoiceState = it.addNewInvoiceState.copy(
                        addNewItemVisible = false, items = it.addNewInvoiceState.items + Item(
                            id = newItem.id.toInt(),
                            code = newItem.code,
                            name = newItem.name,
                            qty = newItem.qty.toDouble(),
                            priceForSell = newItem.priceForSale.toDouble(),
                            priceForBuy = newItem.priceForBuy.toDouble(),
                            price = newItem.priceForSale.toDouble(),
                            total = newItem.qty.toDouble() * newItem.priceForSale.toDouble(),
                        )
                    )
                )
            }

            updateState {
                it.copy(
                    addNewInvoiceState = it.addNewInvoiceState.copy(
                        newInvoiceValues = NewItemValues(),
                    )
                )
            }
            sendNewEffect(SalesUiEffect.ShowToastSuccessAddingNewItemToNewInvoice)

        }, onError = ::onError
        )
    }

    fun onClickAddItemToPreviousInvoice() {
        updateState {
            it.copy(
                addNewInvoiceState = it.addNewInvoiceState.copy(
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        isDoneLoading = true
                    )
                )
            )
        }
        tryToExecute(function = {
            val currentInvoice = state.value.searchInvoice.selectedInvoice
            val newItem = state.value.addNewInvoiceState.newInvoiceValues
            if (currentInvoice?.items?.any {
                    it.id.toString() == newItem.id
                } == true) {
                throw Exception("هذا المنتج موجود بالفعل في الفاتورة")
            }

            if (!state.value.products.any {
                    it.id == newItem.id
                }) {
                throw Exception("برجاء اختيار منتج موجود بالفعل")
            }

            if (state.value.addNewInvoiceState.newInvoiceValues.qty == "") {
                throw Exception("برجاء اضافة الكمية")
            }
            invoiceUseCase.addInvoiceItems(
                currentInvoice?.id.toString(),
                if (currentInvoice?.type == InvoiceType.Amount) "monetary" else "postponed",
                listOf(
                    com.alaa.domain.repository.InvoiceItem(
                        productId = newItem.id.toInt(),
                        quantity = newItem.qty.toInt(),
                        sellingPrice = newItem.priceForSale.toInt(),
                        buyPrice = newItem.priceForBuy.toInt(),
                    )
                )
            )

        }, onSuccess = {

            getAllInvoices()

            updateState {
                it.copy(
                    addNewItemToPreviousInvoice = false,

                    addNewInvoiceState = it.addNewInvoiceState.copy(
                        newInvoiceValues = NewItemValues(),
                    )
                )
            }
            sendNewEffect(SalesUiEffect.ShowToastSuccessAddingNewItemToNewInvoice)

        }, onError = ::onError
        )
    }

    fun onClickDelete(item: Item) {
        tryToExecute(function = {

        }, onSuccess = {
            val items = state.value.addNewInvoiceState.items.toMutableList()
            items.removeIf { it.id == item.id }
            updateState {
                it.copy(
                    addNewInvoiceState = it.addNewInvoiceState.copy(
                        items = items
                    ),
                )
            }
        }, onError = ::onError
        )
    }


    fun onChangeNewInvoiceSupplierNameValue(value: String) {
        val selectedCustomer = state.value.customers.firstOrNull() { it.name == value }
        updateState { it.copy(addNewInvoiceState = it.addNewInvoiceState.copy(customer = selectedCustomer)) }
    }

    fun navigateToAddNewItemToNewInvoice() {
        updateState { it.copy(addNewInvoiceState = it.addNewInvoiceState.copy(addNewItemVisible = true)) }
    }

    fun navigateToAddNewItemToPreviousInvoice() {
        updateState { it.copy(addNewItemToPreviousInvoice = true) }
    }

    fun onChooseNewInvoiceType(value: String) {
        updateState { it.copy(addNewInvoiceState = it.addNewInvoiceState.copy(type = if (value == "اجل") InvoiceType.Later else InvoiceType.Amount)) }
    }

    fun onClickInvoice(invoice: BuyInvoice) {
        updateState {
            it.copy(
                searchInvoice = it.searchInvoice.copy(
                    selectedInvoice = invoice
                )
            )
        }
    }

    private fun onError(error: ErrorUiState, message: String) {
        updateState {
            it.copy(
                isLoading = false, addNewInvoiceState = it.addNewInvoiceState.copy(
                    isSaveLoading = false,
                    newInvoiceValues = it.addNewInvoiceState.newInvoiceValues.copy(
                        isDoneLoading = false
                    )
                )
            )
        }
        Log.e("TAG", "onError: $message")
        sendNewEffect(SalesUiEffect.ShowToastError(message))
    }

    fun onClickDeleteInvoice() {
        tryToExecute(function = {
            invoiceUseCase.deleteInvoice(state.value.searchInvoice.selectedInvoice?.id.toString())
        }, onSuccess = {
            getAllInvoices()
            sendNewEffect(SalesUiEffect.ShowToastError(it))
            updateState {
                it.copy(
                    searchInvoice = it.searchInvoice.copy(
                        selectedInvoice = null
                    )
                )
            }
        }, onError = ::onError
        )
    }

    fun onClickDeleteItemInPreviousInvoice(currentItem: Item) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(function = {
            invoiceUseCase.deleteItemInvoice(currentItem.id.toString())
        }, onSuccess = {
            getAllInvoices()
            sendNewEffect(SalesUiEffect.ShowToastError(it))
            updateState {
                it.copy(
                    isLoading = false,
                    searchInvoice = it.searchInvoice.copy(
                        selectedInvoice = null
                    )
                )
            }
        }, onError = ::onError
        )
    }
}