package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditAccountInvoiceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val manageAccountsUseCase: ManageAccountsUseCase
) : BaseViewModel<EditAccountInvoiceState, EditAccountInvoiceEffect>(EditAccountInvoiceState()) {
    private val args = EditAccountInvoiceArgs(savedStateHandle)

    init {
        updateState {
            it.copy(invoiceId = args.invoiceId)
        }
        getInvoice()
    }

    private fun getInvoice() {
        if (state.value.invoiceId != -1) {

            updateState {
                it.copy(isLoading = true)
            }
            tryToExecute(
                function = {
                    manageAccountsUseCase.getInvoice(
                        state.value.invoiceId
                    )
                },
                onSuccess = { data ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            amount = data.amount.toString(),
                            baseAmount = data.amount.toString(),
                            date = data.dateTime,
                            accountCustomerId = data.accountCustomerId,
                            isCreditor = data.type == "creditor",
                            baseIsCreditor = data.type == "creditor",
                            additionalNotes = data.moreInfo,
                            baseAdditionalNotes = data.moreInfo,
                            imageUrl = data.media
                        )
                    }
                },
                onError = { _, m ->
                    updateState {
                        it.copy(isLoading = false, error = m)
                    }
                    sendNewEffect(EditAccountInvoiceEffect.ShowToastError(m))
                }
            )
        }

    }


    fun amountChange(value: String) {
        updateState {
            it.copy(amount = value)
        }
    }

    fun onChangeImage(uri: Uri?, file: File?) {
        updateState {
            it.copy(image = file, imageUri = uri)
        }
    }

    fun additionalNotesChange(additionalNotes: String) {
        updateState {
            it.copy(additionalNotes = additionalNotes)
        }
    }

    fun changeCreditor(isCreditor: Boolean) {
        updateState {
            it.copy(isCreditor = isCreditor)
        }
    }

    fun addOperation() {
        updateState {
            it.copy(
                isLoadingAddOperation = true
            )
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.updateInvoice(
                    invoiceId = state.value.invoiceId,
                    accountCustomerId = state.value.accountCustomerId,
                    date = state.value.date,
                    amount = state.value.amount,
                    type = if (state.value.isCreditor) "creditor" else "debtor",
                    additionalNote = state.value.additionalNotes,
                    image = state.value.image
                )
            },
            onError = { _, message ->
                sendNewEffect(EditAccountInvoiceEffect.ShowToastError(message))
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
            },
            onSuccess = {
                sendNewEffect(EditAccountInvoiceEffect.SucceedEditAccountInvoice)
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
            }
        )
    }

    fun deleteOperation() {
        updateState {
            it.copy(
                isLoadingAddOperation = true
            )
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.deleteInvoice(
                    invoiceId = state.value.invoiceId
                )
            },
            onError = { _, message ->
                sendNewEffect(EditAccountInvoiceEffect.ShowToastError(message))
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
            },
            onSuccess = {
                sendNewEffect(EditAccountInvoiceEffect.SucceedDeleteAccountInvoice)
                updateState {
                    it.copy(isLoadingAddOperation = false)
                }
            }
        )
    }

    fun onDateChange(date: String) {
        updateState {
            it.copy(
                date = date
            )
        }
    }

}