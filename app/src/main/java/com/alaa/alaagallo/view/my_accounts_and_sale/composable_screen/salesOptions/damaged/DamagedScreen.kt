package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.damaged

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.Constants.DAMAGED_PDF_URL
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.home.Home
import com.alaa.alaagallo.view.my_accounts_and_sale.base.EventHandler
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.AddButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SearchField
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DamagedScreen(viewModel: DamagedViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    val pdfUrl =DAMAGED_PDF_URL

    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is DamagedUiEffect.ShowToastError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is DamagedUiEffect.ShowToastSuccessAddingDamaged -> {
                Toast.makeText(context, "تم اضافة هالك بنجاح", Toast.LENGTH_SHORT).show()
            }

            is DamagedUiEffect.ShowToastSuccessEditingDamaged -> {
                Toast.makeText(context, "تم تعديل هالك بنجاح", Toast.LENGTH_SHORT).show()
            }

            is DamagedUiEffect.NavigateBack -> {
                navController.popBackStack()
            }

            else -> TODO()
        }
    }
    DamagedContent(
        state,
        viewModel,
        onClickPdf = { id ->
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(pdfUrl + id), "application/pdf")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "لا يوجد تطبيق لعرض PDF", Toast.LENGTH_SHORT).show()
            }
        },
        onClickPrinter = { id ->
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(
                    Intent.EXTRA_STREAM,
                    Uri.parse(pdfUrl + id)
                ) // you must convert the URL to Uri
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            try {
                context.startActivity(Intent.createChooser(intent, "اختر تطبيق لمشاركة الملف"))
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DamagedContent(
    state: DamagedUiState,
    viewModel: DamagedViewModel,
    onClickPdf: (Int) -> Unit,
    onClickPrinter: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    SalesScaffold(
        title = if (state.isAddingNew) "اضافة هالك" else "الهوالك",
        actions = {
            if (!state.isAddingNew) {
                IconButton(onClick = {
                    onClickPdf(Home.id)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_pdf_document),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    onClickPrinter(Home.id)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.printer_ic),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        },
        navBack = { viewModel.navigateBackToDetails() }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        } else {
            AnimatedVisibility(state.isAddingNew) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onChangeNameValue,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "نوع الهالك",
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = state.price,
                        onValueChange = viewModel::onChangeAmountValue,
                        modifier = Modifier.fillMaxWidth(),
                        //trailingIcon = { Icon(Icons.Default.AccountBox, "") },
                        label = {
                            Text(
                                "السعر",
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(10.dp)
                    )
                    DatePickerField(
                        value = state.date,
                        onValueChange = viewModel::onChangeDateValue,
                    )
                    Spacer(Modifier.height(50.dp))

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.textButtonColors(containerColor = Color(0xff00BA2F)),
                        onClick = viewModel::onClickDone
                    ) {
                        if (state.isDoneLoading)
                            LoadingIndicator()
                        else
                            Text(
                                if (state.isEdit) "تعديل" else "أضافة",
                                color = Color.White,
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600
                                )
                            )
                    }
                }
            }
            AnimatedVisibility(!state.isAddingNew) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                    ) {
                        stickyHeader {
                            Column(modifier = Modifier.background(Color.White)) {
                                SearchField(
                                    value = state.searchValue,
                                    onValueChange = viewModel::onChangeSearchValue
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(Color(0xffEDEDED)),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        "اسم المنتج" to 1.5f,
                                        "التاريخ" to 1.5f,
                                        "السعر" to 1f,
                                    ).forEach { pair: Pair<String, Float> ->
                                        Box(
                                            modifier = Modifier
                                                .height(48.dp)
                                                .weight(pair.second)
                                                .border(1.dp, Color(0xffEDEDED))
                                                .padding(horizontal = 4.dp)
                                                .align(Alignment.CenterVertically),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = pair.first,
                                                style = Theme.typography.footerAddOption.copy(
                                                    fontWeight = FontWeight.Black,
                                                    textAlign = TextAlign.Center
                                                ),
                                            )
                                        }
                                    }
                                }
                            }

                        }
                        items(state.damaged.filter {
                            it.name.contains(state.searchValue)
                        }.size) { index ->
                            val currentDamaged = state.damaged.filter {
                                it.name.contains(state.searchValue)
                            }[index]
                            val swipeState = rememberSwipeToDismissBoxState()

                            SwipeToDismissBox(
                                rememberSwipeToDismissBoxState(),
                                backgroundContent = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .background(Color.Red),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    swipeState.reset()
                                                }
                                            }) {
                                                Icon(Icons.Default.Refresh, "", tint = Color.White)
                                            }
                                            IconButton(onClick = {
                                                if (swipeState.targetValue == SwipeToDismissBoxValue.StartToEnd)
                                                    viewModel.onClickDelete(currentDamaged)
                                            }) {
                                                Icon(Icons.Default.Delete, "", tint = Color.White)
                                            }
                                        }
                                    }
                                }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .clickable {
                                            viewModel.onClickItem(currentDamaged)
                                        }
                                        .background(Color.White),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        currentDamaged.name to 1.5f,
                                        currentDamaged.date to 1.5f,
                                        currentDamaged.price.toString() to 1f,
                                    ).forEach { pair: Pair<String, Float> ->
                                        Box(
                                            modifier = Modifier
                                                .height(48.dp)
                                                .weight(pair.second)
                                                .border(0.25.dp, Color(0xffEDEDED))
                                                .padding(horizontal = 4.dp)
                                                .align(Alignment.CenterVertically),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = pair.first,
                                                style = Theme.typography.headerPhone.copy(
                                                    textAlign = TextAlign.Center
                                                ),
                                                modifier = Modifier,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    AddButton(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = "اضافة هوالك",
                        icon = R.drawable.ic_add_invoice
                    ) {
                        viewModel.navigateToAddNew()
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth(),
        //.clickable { showDatePicker = true },
        readOnly = true,
        label = {
            Text(
                "تاريخ الانتهاء",
                color = Color(0xffBFBFBF),
                style = Theme.typography.headerPhone.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xff515151),
            focusedBorderColor = Color(0xff515151)
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            IconButton(onClick = { showModal = !showModal }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
    )


    if (showModal) {
        DatePickerModal(
            initialValue = try {
                LocalDate.parse(value)
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            } catch (ex: Throwable) {
                null
            },
            onDateSelected = {
                it?.let { millis ->
                    val selectedDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    val formattedDate = selectedDate.format(DateTimeFormatter.ISO_DATE)
                    onValueChange(formattedDate)
                }
            },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialValue: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialValue)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


