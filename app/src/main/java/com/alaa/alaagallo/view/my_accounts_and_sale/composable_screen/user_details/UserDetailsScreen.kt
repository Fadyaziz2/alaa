package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.toDate
import com.alaa.alaagallo.toFormatString
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.home.Home
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.CustomDatePickerDialog
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FABButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FormTextField
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.HeaderText
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.ItemCameraOrFile
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.NegativeDialogButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.PositiveButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.TableInvoiceItem
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice.navigateToEditAccountInvoice
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.util.Date

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    UserDetailsContent(
        state = state,
        effect = viewModel.effect,
        onCheckedChanged = viewModel::checkedChanged,
        onChangeCreditor = viewModel::changeCreditor,
        onAmountChange = viewModel::amountChange,
        onChangeImage = viewModel::onChangeImage,
        onAdditionalNoteChanged = viewModel::additionalNotesChange,
        addOperation = viewModel::addOperation,
        onDateChange = viewModel::onDateChange,
        onClickPdf = {id->
            val pdfUrl = "https://alaa-galoo.com/account/customer/export/"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(pdfUrl + id), "application/pdf")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "لا يوجد تطبيق لعرض PDF", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun UserDetailsContent(
    state: UserDetailsState,
    effect: SharedFlow<UserDetailsEffect?>,
    onAmountChange: (String) -> Unit,
    onAdditionalNoteChanged: (String) -> Unit,
    onChangeCreditor: (Boolean) -> Unit,
    onChangeImage: (Uri?, File?) -> Unit,
    onCheckedChanged: (String) -> Unit,
    onDateChange: (String) -> Unit,
    addOperation: () -> Unit,
    onClickPdf: (Int) -> Unit
) {
    val navController = LocalNavigationProvider.current
    val context = LocalContext.current
    var showBottomArrangement by remember {
        mutableStateOf(false)
    }
    val bottomArrangementState = rememberModalBottomSheetState()
    var showBottomAddInvoice by remember {
        mutableStateOf(false)
    }
    val bottomAddInvoiceState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedDate by remember { mutableStateOf(Date().toFormatString("dd-MM-yyyy")) }
    var selectedDateLong by remember { mutableLongStateOf(Date().time) }
    var showCalendarDate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        effect.collectLatest { effect ->
            when (effect) {
                is UserDetailsEffect.ShowToastError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is UserDetailsEffect.SucceedAddOperation -> {
                    Toast.makeText(context, "تم اضافة العملية بنجاح", Toast.LENGTH_SHORT).show()
                    showBottomAddInvoice = false
                }

                else -> TODO()
            }
        }
    }
    LaunchedEffect(showBottomArrangement) {
        if (showBottomArrangement)
            bottomArrangementState.show()
        else
            bottomArrangementState.hide()
    }
    LaunchedEffect(showBottomAddInvoice) {
        if (showBottomAddInvoice)
            bottomAddInvoiceState.show()
        else
            bottomAddInvoiceState.hide()
    }
    Scaffold { _ ->
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(Color.Black)
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 23.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        Spacer(Modifier.width(20.dp))
                        Text(
                            state.userName,
                            style = Theme.typography.headerMainTitle,
                            color = Color.White
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(
                            state.userPhone,
                            style = Theme.typography.headerMainTitle,
                            color = Color.White
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = {
                            onClickPdf(state.clientId)
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_pdf_document),
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        showBottomArrangement = true
                                    }
                                })
                    }
                }
                AnimatedContent(state.isLoading, label = "") {
                    if (it) {
                        Box(
                            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            LoadingIndicator()
                        }
                    } else {
                        Column(Modifier) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(Theme.colors.yellow)
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "اجمالي المستحقات: ${state.totalOfCreditorInvoices}",
                                    style = Theme.typography.tableHeader.copy(
                                        fontSize = 13.sp, lineHeight = 24.36.sp
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "اجمالي الديون: ${state.totalOfDebtorInvoices}",
                                    style = Theme.typography.tableHeader.copy(
                                        fontSize = 13.sp, lineHeight = 24.36.sp
                                    ),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                item {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .background(Theme.colors.greyTableHeader)
                                            .padding(horizontal = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        HeaderText(
                                            modifier = Modifier.weight(2f),
                                            text = "تاريخ العملية"
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        HeaderText(
                                            modifier = Modifier.weight(1f),
                                            text = "المبلغ"
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        HeaderText(
                                            modifier = Modifier.weight(1f),
                                            text = "معلومات اضافية"
                                        )
                                    }
                                }
                                items(state.invoices) { invoice ->
                                    TableInvoiceItem(invoice) {
                                        navController.navigateToEditAccountInvoice(invoice.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            FABButton(
                modifier = Modifier.align(Alignment.BottomStart),
                text = "عملية جديدة",
                icon = R.drawable.ic_add_invoice,
                isLoading = state.isLoadingAddOperation
            ) {
                showBottomAddInvoice = true
            }
            if (showBottomArrangement) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomArrangement = false },
                    sheetState = bottomArrangementState,
                    containerColor = Color.White
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("طريقة عرض العمليات", style = Theme.typography.titleDialog)
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = Theme.colors.greyDivider)
                        Spacer(Modifier.height(19.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                modifier = Modifier.size(5.dp),
                                selected = state.sort == "asc",
                                onClick = {
                                    if (state.sort == "desc") {
                                        onCheckedChanged("asc")
                                        showBottomArrangement = false
                                    }
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Theme.colors.blueRadioButton,
                                    unselectedColor = Theme.colors.blueRadioButton
                                )
                            )
                            Spacer(Modifier.width(20.dp))
                            Text("تصاعدى حسب تاريخ العمليات")
                        }
                        Spacer(Modifier.height(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                modifier = Modifier.size(5.dp),
                                selected = state.sort == "desc",
                                onClick = {
                                    if (state.sort == "asc") {
                                        onCheckedChanged("desc")
                                        showBottomArrangement = false
                                    }
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Theme.colors.blueRadioButton,
                                    unselectedColor = Theme.colors.blueRadioButton
                                )
                            )
                            Spacer(Modifier.width(20.dp))
                            Text("تنازلي حسب تاريخ العمليات")
                        }
                    }
                }
            }
            if (showBottomAddInvoice) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomAddInvoice = false },
                    sheetState = bottomAddInvoiceState,
                    containerColor = Color.White
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("عملية جديدة", style = Theme.typography.titleDialog)
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = Theme.colors.greyDivider)
                        Spacer(Modifier.height(10.dp))
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
                                        showCalendarDate = true
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
                            ItemCameraOrFile(state.imageUri) { uri, file ->
                                onChangeImage(uri, file)
                            }
                        }
                        Spacer(Modifier.height(30.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            NegativeDialogButton {
                                showBottomAddInvoice = false
                            }
                            Spacer(Modifier.width(30.dp))
                            PositiveButton(
                                text = "اضافة العملية",
                                isLoading = state.isLoadingAddOperation,
                                isEnabled = state.visibilityAddOperationButton,
                                modifier = Modifier.size(128.dp, 48.dp)
                            ) {
                                addOperation()
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
    }
}