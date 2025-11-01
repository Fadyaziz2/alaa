package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.toDate
import com.alaa.alaagallo.toFormatString
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.CustomDatePickerDialog
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FormTextField
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.ItemCameraOrFile2
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.NegativeDialogButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.PositiveButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client.EditAccountClientEffect
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.util.Date

@Composable
fun EditAccountInvoiceScreen(viewModel: EditAccountInvoiceViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is EditAccountInvoiceEffect.ShowToastError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is EditAccountInvoiceEffect.SucceedEditAccountInvoice -> {
                    Toast.makeText(context, "تم تعديل العميل بنجاح", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is EditAccountInvoiceEffect.SucceedDeleteAccountInvoice -> {
                    Toast.makeText(context, "تم حذف العميل بنجاح", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }

                else -> TODO()
            }
        }
    }


    EditAccountInvoiceContent(
        state = state,
        onAmountChange = viewModel::amountChange,
        onAdditionalNoteChanged = viewModel::additionalNotesChange,
        onChangeCreditor = viewModel::changeCreditor,
        onChangeImage = viewModel::onChangeImage,
        onDateChange = viewModel::onDateChange,
        addOperation = viewModel::addOperation,
        deleteInvoice = viewModel::deleteOperation
    )
}


@Composable
private fun EditAccountInvoiceContent(
    state: EditAccountInvoiceState,
    onAmountChange: (String) -> Unit,
    onAdditionalNoteChanged: (String) -> Unit,
    onChangeCreditor: (Boolean) -> Unit,
    onChangeImage: (Uri?, File?) -> Unit,
    onDateChange: (String) -> Unit,
    addOperation: () -> Unit,
    deleteInvoice: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(Date().toFormatString("dd-MM-yyyy")) }
    var selectedDateLong by remember { mutableLongStateOf(Date().time) }
    var showCalendarDate by remember { mutableStateOf(false) }
    val navController = LocalNavigationProvider.current
    Box(Modifier.fillMaxSize().background(Color.White)) {
        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize().background(Color.White), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    strokeWidth = 4.dp,
                    color = Theme.colors.orange
                )
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Black)
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 23.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(5.dp)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    navController.popBackStack()
                                }
                            })
                    Spacer(Modifier.weight(1f))
                    Text(
                        "تعديل عملية",
                        style = Theme.typography.headerMainTitle,
                        color = Color.White
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier.size(20.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FormTextField(
                            modifier = Modifier
                                .height(52.dp)
                                .weight(1f),
                            text = state.amount,
                            onValueChange = {
                                onAmountChange(it)
                            },
                            hint = "المبلغ"
                        )
                        Spacer(Modifier.width(10.dp))
                        Box(
                            Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    width = 0.5.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    color = Theme.colors.greyBorder2
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_phone),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    FormTextField(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectTapGestures {
//                                showCalendarDate = true
                                }
                            },
                        text = state.date,
                        enabled = false,
                        onValueChange = {},
                        hint = "",
                        trailing = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .height(48.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    0.5.dp,
                                    Theme.colors.greyBorder2,
                                    RoundedCornerShape(10.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        if (!state.isCreditor) {
                                            onChangeCreditor(true)
                                        }
                                    }
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 15.dp)
                            ) {
                                RadioButton(
                                    modifier = Modifier.size(5.dp),
                                    selected = state.isCreditor,
                                    onClick = {
                                        if (!state.isCreditor) {
                                            onChangeCreditor(true)
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Theme.colors.greenButton,
                                        unselectedColor = Theme.colors.greenButton
                                    )
                                )
                                Spacer(Modifier.width(20.dp))
                                Text("دائن")
                            }
                        }
                        Spacer(Modifier.width(20.dp))
                        Box(
                            Modifier
                                .height(48.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    0.5.dp,
                                    Theme.colors.greyBorder2,
                                    RoundedCornerShape(10.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        if (state.isCreditor) {
                                            onChangeCreditor(false)
                                        }
                                    }
                                },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 15.dp)
                            ) {
                                RadioButton(
                                    modifier = Modifier.size(5.dp),
                                    selected = !state.isCreditor,
                                    onClick = {
                                        if (state.isCreditor) {
                                            onChangeCreditor(false)
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Theme.colors.red,
                                        unselectedColor = Theme.colors.red
                                    )
                                )
                                Spacer(Modifier.width(20.dp))
                                Text("مدين")
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    FormTextField(
                        modifier = Modifier
                            .height(125.dp)
                            .fillMaxWidth(),
                        isSingleLine = false,
                        isNumber = false,
                        text = state.additionalNotes,
                        onValueChange = {
                            onAdditionalNoteChanged(it)
                        },
                        hint = "معلومات اضافية",
                    )
                    Spacer(Modifier.height(10.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(137.dp)
                            .drawBehind {
                                drawRoundRect(
                                    cornerRadius = CornerRadius(20f, 20f),
                                    color = Color(0xff515151), // Border color
                                    style = Stroke(
                                        width = 4f,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                10f,
                                                10f
                                            )
                                        ) // Dashed effect
                                    )
                                )
                            },
                    ) {
                        ItemCameraOrFile2(state.imageUri, url = state.imageUrl ?: "") { uri, file ->
                            onChangeImage(uri, file)
                        }
                    }
                    Spacer(Modifier.height(30.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NegativeDialogButton(
                            text = "حذف العمليه",
                            modifier = Modifier.height(48.dp)
                        ) {
                            deleteInvoice()
                        }
                        Spacer(Modifier.width(10.dp))
                        PositiveButton(
                            text = "تعديل العملية",
                            isLoading = state.isLoadingAddOperation,
                            isEnabled = state.visibilityAddOperationButton,
                            modifier = Modifier.height(48.dp)
                        ) {
                            addOperation()
                        }
                    }
                }

            }
        }

    }
    if (showCalendarDate) {
        CustomDatePickerDialog(
            selectedDate = selectedDateLong,
            onDismiss = { showCalendarDate = false },
            onDateSelected = { timeLong ->
                timeLong?.let { time ->
                    selectedDateLong = time
                    selectedDate = time.toDate("yyyy-MM-dd")
                    onDateChange(selectedDate)
                }
            }
        )
    }
}