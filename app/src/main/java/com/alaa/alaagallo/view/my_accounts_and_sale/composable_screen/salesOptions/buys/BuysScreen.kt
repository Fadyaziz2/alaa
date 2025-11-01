package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.base.EventHandler
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.AddButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.CustomDropDownMenu
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.LoadingContent
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SearchField
import kotlinx.coroutines.launch
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.alaa.Constants.INVOICE_ITEMS_PDF_URL

@Composable
fun BuysScreen(viewModel: BuysViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    val pdfUrl = INVOICE_ITEMS_PDF_URL

    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is BuysUiEffect.ShowToastError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is BuysUiEffect.NavigateBack -> {
                navController.popBackStack()
            }

            is BuysUiEffect.ShowToastSuccessAddingNewItemToNewInvoice -> {
                Toast.makeText(context, "تم اضافة المنتج بنجاح", Toast.LENGTH_SHORT).show()
            }

            is BuysUiEffect.ShowToastSuccessAddingNewInvoice -> {
                Toast.makeText(context, "تم اضافة الفاتوره بنجاح", Toast.LENGTH_SHORT).show()
            }

            else -> TODO()
        }
    }
    BuysContent(
        state, viewModel,
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
        onClickPrinter = {id ->
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, Uri.parse(pdfUrl + id)) // you must convert the URL to Uri
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            try {
                context.startActivity(Intent.createChooser(intent, "اختر تطبيق الطباعة"))
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        },
        onClickShare = {id ->
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, Uri.parse(pdfUrl + id)) // you must convert the URL to Uri
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BuysContent(
    state: BuysUiState,
    viewModel: BuysViewModel,
    onClickPdf: (Int) -> Unit,
    onClickShare: (Int) -> Unit,
    onClickPrinter: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()

    var isVisiblePriceForBuy by remember { mutableStateOf(false) }
    var isVisiblePriceForSale by remember { mutableStateOf(false) }




    SalesScaffold(title = if (!state.searchInvoice.visible && !state.addNewInvoiceState.visible) ""
    else if (state.searchInvoice.visible && state.searchInvoice.selectedInvoice != null) state.searchInvoice.selectedInvoice.supplierName
    else if (state.addNewInvoiceState.visible && !state.addNewInvoiceState.addNewItemVisible) "فاتورة المشتريات جديدة"
    else if (state.addNewInvoiceState.visible && state.addNewInvoiceState.addNewItemVisible) "اضافة منتج في فاتورة المشتريات"
    else "فواتير المشتريات", navBack = { viewModel.navigateBackToDetails() }, actions = {
        if (state.addNewInvoiceState.visible && !state.addNewInvoiceState.addNewItemVisible) {
            TextButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(35.dp)
                    .padding(end = 12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(containerColor = Color(0xff00BA2F)),
                onClick = viewModel::onClickSaveNewInvoice
            ) {
                if (state.addNewInvoiceState.isSaveLoading) LoadingIndicator()
                else Text("حفظ الفاتورة", color = Color.White , style = Theme.typography.footerAddOption.copy(fontSize = 12.sp , fontWeight = FontWeight.W600))
            }
        }
        if (state.searchInvoice.visible && state.searchInvoice.selectedInvoice != null && !state.addNewItemToPreviousInvoice) {
            IconButton(onClick = {
                onClickShare(state.searchInvoice.selectedInvoice.id)
            }) {
                Icon(
                    painter = painterResource(R.drawable.share_ic),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                onClickPdf(state.searchInvoice.selectedInvoice.id)
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_pdf_document),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                onClickPrinter(state.searchInvoice.selectedInvoice.id)
            }) {
                Icon(
                    painter = painterResource(R.drawable.printer_ic),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
    ) { paddingValues ->
        LoadingContent(state.isLoading) {
            AnimatedVisibility(
                !state.searchInvoice.visible && !state.addNewInvoiceState.visible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { viewModel.onNavigateToAddNewInvoice() }
                        .drawBehind {
                            val strokeWidth = 0.5.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color(0xffFFC801),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(start = 10.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Row {
                            Image(
                                painter = painterResource(R.drawable.add_new),
                                modifier = Modifier.size(20.dp),
                                contentDescription = ""
                            )
                            Spacer(Modifier.width(10.dp))
                            Text("اضافة فاتورة مشتريات", style = Theme.typography.footerAddOption)
                        }
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { viewModel.onNavigateToSearchInvoice() }
                        .drawBehind {
                            val strokeWidth = 0.5.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color(0xffFFC801),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(start = 10.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Row {
                            Image(
                                painter = painterResource(R.drawable.search_on),
                                modifier = Modifier.size(20.dp),
                                contentDescription = ""
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                "البحث عن فاتورة مشتريات",
                                style = Theme.typography.footerAddOption
                            )
                        }
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "",
                            modifier = Modifier.rotate(180f)
                        )
                    }

                }
            }
            AnimatedVisibility(
                state.searchInvoice.visible && state.searchInvoice.selectedInvoice == null,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 76.dp)
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
                                        "اسم المورد" to 2f,
                                        "التاريخ" to 2f,
                                        "الكمية" to 1f,
                                        "الاجمالي" to 2f,
                                        "الدفع" to 1f,
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
                        items(state.searchInvoice.invoices.filter {
                            it.supplierName.contains(state.searchValue)
                        }.size) { index ->
                            val currentInvoice = state.searchInvoice.invoices.filter {
                                it.supplierName.contains(state.searchValue)
                            }[index]

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .clickable {
                                        viewModel.onClickInvoice(currentInvoice)
                                    }
                                    .background(Color.White),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                listOf(
                                    currentInvoice.supplierName to 2f,
                                    currentInvoice.dateTime to 2f,
                                    currentInvoice.qty.toString() to 1f,
                                    currentInvoice.total.toString() to 2f,
                                    currentInvoice.type.toString() to 1f,
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
                                            text = if (pair.first == "Amount") "نقدي" else if (pair.first == "Later") "اجل" else pair.first,
                                            style = Theme.typography.headerPhone.copy(
                                                textAlign = TextAlign.Center
                                            ),
                                            modifier = Modifier,
                                            color = if (pair.first == "Amount") Color(0xff00BA2F) else if (pair.first == "Later") Color(
                                                0xffFD0808
                                            ) else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(76.dp)
                            .fillMaxWidth()
                            .background(Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.matchParentSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "النقدي : ${state.searchInvoice.invoices.count { it.type == InvoiceType.Amount }}  فاتورة",
                                style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                modifier = Modifier,
                                color = Color.White
                            )
                            Text(
                                text = "الأجل : ${state.searchInvoice.invoices.count { it.type == InvoiceType.Later }}  فاتورة",
                                style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                modifier = Modifier,
                                color = Color.White
                            )

                        }
                    }
//                    AddButton(
//                        modifier = Modifier.align(Alignment.BottomStart),
//                        text = "اضافة عميل",
//                        icon = R.drawable.ic_add_client
//                    ) {
//                        viewModel.navigateToAddNew()
//                    }
                }
            }
            AnimatedVisibility(
                state.searchInvoice.visible && state.searchInvoice.selectedInvoice != null && !state.addNewItemToPreviousInvoice,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                val selectedInvoice =
                    state.searchInvoice.invoices.filter { state.searchInvoice.selectedInvoice?.id == it.id }
                        .firstOrNull()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                    ) {
                        stickyHeader {
                            Column(
                                modifier = Modifier.background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = selectedInvoice?.dateTime ?: "",
                                    style = Theme.typography.footerAddOption.copy(
                                        fontWeight = FontWeight.Black, textAlign = TextAlign.Center
                                    ),
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(Color(0xffEDEDED)),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        "كود المنتج" to 2f,
                                        "اسم المنتج" to 2f,
                                        "الكمية" to 1f,
                                        "سعر البيع" to 2f,
                                        "اجمالي" to 1f,
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
                        items(selectedInvoice?.items?.size ?: 0) { index ->
                            val currentItem = selectedInvoice?.items?.get(index)!!
                            val swipeState = rememberSwipeToDismissBoxState()
                            SwipeToDismissBox(rememberSwipeToDismissBoxState(),
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
                                                    viewModel.onClickDeleteItemInPreviousInvoice(
                                                        currentItem
                                                    )
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
                                        .background(Color.White),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        currentItem.code to 2f,
                                        currentItem.name to 2f,
                                        currentItem.qty.toString() to 1f,
                                        currentItem.price.toString() to 2f,
                                        currentItem.total.toString() to 1f,
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(76.dp)
                            .fillMaxWidth()
                            .background(Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.matchParentSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "الدفع : " + (if (selectedInvoice?.type == InvoiceType.Amount) "نقدي" else "اجل"),
                                style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                modifier = Modifier,
                                color = Color.White
                            )
                            Text(
                                text = "اجمالي سعر الفاتورة : ${selectedInvoice?.total}",
                                style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                modifier = Modifier,
                                color = Color.White
                            )

                        }
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 76.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AddButton(
                            modifier = Modifier,
                            text = "اضافة منتج للفاتورة",
                            icon = R.drawable.ic_add_invoice_with_white_bg,
                            width = 170.dp,
                            roundCornerRadius = 5.dp,
                            borderColor = Color(0xffFF8E5A),
                            border = true
                        ) {
                            viewModel.navigateToAddNewItemToPreviousInvoice()
                        }
                        AddButton(
                            modifier = Modifier,
                            text = "حذف الفاتورة",
                            icon = R.drawable.delete_filled_1,
                            width = 170.dp,
                            roundCornerRadius = 5.dp,
                            borderColor = Color(0xffFD0808),
                            border = true
                        ) {
                            viewModel.onClickDeleteInvoice()
                        }
                    }
                }
            }
            AnimatedVisibility(
                state.searchInvoice.visible && state.searchInvoice.selectedInvoice != null && state.addNewItemToPreviousInvoice,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.code,
                        onValueChange = viewModel::onChangeCodeInNewItemValue,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.searchProductWithBarcode() }) {
                                Icon(painterResource(R.drawable.barcode_1), "")
                            }
                        },
                        label = {
                            Text(
                                "كود المنتج",
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
                    CustomDropDownMenu(
                        label = "اسم المنتج",
                        options = state.products.map { Pair(it.id.toLongOrNull() ?: -1L, it.name) },
                        selectedOption = Pair(
                            state.addNewInvoiceState.newInvoiceValues.id.toLongOrNull() ?: -1L,
                            state.addNewInvoiceState.newInvoiceValues.name
                        ),
                        onClickOption = {
                            viewModel.searchProductUsingId(it.first)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.qty,
                        onValueChange = viewModel::onChangeQtyInNewItemValue,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                "الكمية",
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = state.addNewInvoiceState.newInvoiceValues.priceForBuy,
                            onValueChange = viewModel::onChangePriceForBuyInNewItemValue,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (isVisiblePriceForBuy) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isVisiblePriceForBuy)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                IconButton(onClick = {
                                    isVisiblePriceForBuy = !isVisiblePriceForBuy
                                }) {
                                    Icon(imageVector = image, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            label = {
                                Text(
                                    "سعر الشراء",
                                    color = Color(0xffBFBFBF),
                                    textAlign = TextAlign.Start,
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
                            minLines = 1,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = state.addNewInvoiceState.newInvoiceValues.priceForSale,
                            onValueChange = viewModel::onChangePriceForSaleInNewItemValue,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (isVisiblePriceForSale) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isVisiblePriceForSale)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                IconButton(onClick = {
                                    isVisiblePriceForSale = !isVisiblePriceForSale
                                }) {
                                    Icon(imageVector = image, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            label = {
                                Text(
                                    "سعر البيع",
                                    color = Color(0xffBFBFBF),
                                    textAlign = TextAlign.Start,
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
                            minLines = 1,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.orderLimit,
                        onValueChange = viewModel::onChangeOrderLimitInNewItemValue,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "حد الطلب",
                                color = Color(0xffBFBFBF),
                                textAlign = TextAlign.Start,
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
                        minLines = 1,
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(Modifier.height(50.dp))

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.textButtonColors(containerColor = Color(0xff00BA2F)),
                        onClick = viewModel::onClickAddItemToPreviousInvoice
                    ) {
                        if (state.addNewInvoiceState.newInvoiceValues.isDoneLoading) LoadingIndicator()
                        else Text(
                            "اضافة المنتج",
                            color = Color.White,
                            style = Theme.typography.headerPhone.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                }
            }
            AnimatedVisibility(
                state.addNewInvoiceState.visible && !state.addNewInvoiceState.addNewItemVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                    ) {
                        stickyHeader {
                            Column(
                                modifier = Modifier.background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "بعد الحفظ سيتم اضافة المنتجات للمخزن تلقائي",
                                    style = Theme.typography.footerAddOption.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center,
                                        color = Color(0xff00BA2F)
                                    ),
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                        .padding(bottom = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CustomDropDownMenu(
                                        label = "اسم الزوبون",
                                        options = state.customers.map { it.name },
                                        selectedOption = state.addNewInvoiceState.customer?.name
                                            ?: "",
                                        onClickOption = {
                                            viewModel.onChangeNewInvoiceSupplierNameValue(
                                                it
                                            )
                                        },
                                        modifier = Modifier.weight(3f)
                                    )
                                    CustomDropDownMenu(
                                        label = "",
                                        options = listOf("نقدي", "اجل"),
                                        selectedOption = if (state.addNewInvoiceState.type == InvoiceType.Later) "اجل" else "نقدي",
                                        onClickOption = { viewModel.onChooseNewInvoiceType(it) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
//                                OutlinedTextField(
//                                    value = state.addNewInvoiceState.supplierName,
//                                    onValueChange = viewModel::onChangeNewInvoiceSupplierNameValue,
//                                    modifier = Modifier.fillMaxWidth(),
//                                    label = { Text("اسم العميل", color = Color(0xffBFBFBF)) },
//                                    colors = OutlinedTextFieldDefaults.colors(
//                                        unfocusedBorderColor = Color(0xff515151),
//                                        focusedBorderColor = Color(0xff515151)
//                                    ),
//                                    shape = RoundedCornerShape(10.dp)
//                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(Color(0xffEDEDED)),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        "كود المنتج" to 2f,
                                        "اسم المنتج" to 2f,
                                        "الكمية" to 1f,
                                        "سعر البيع" to 2f,
                                        "اجمالي" to 1f,
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
                        items(state.addNewInvoiceState.items.size) { index ->
                            val currentItem = state.addNewInvoiceState.items[index]
                            val swipeState = rememberSwipeToDismissBoxState()
                            SwipeToDismissBox(rememberSwipeToDismissBoxState(),
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
                                                    viewModel.onClickDelete(currentItem)
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
                                        .background(Color.White),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        currentItem.code to 2f,
                                        currentItem.name to 2f,
                                        currentItem.qty.toString() to 1f,
                                        currentItem.price.toString() to 2f,
                                        currentItem.total.toString() to 1f,
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(76.dp)
                            .fillMaxWidth()
                            .background(Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.matchParentSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "اجمالي سعر الفاتورة : ${state.addNewInvoiceState.items.sumOf { it.total }}",
                                style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                modifier = Modifier,
                                color = Color.White
                            )
                        }
                    }
                    AddButton(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 76.dp),
                        text = "اضافة منتج للفاتورة",
                        icon = R.drawable.ic_add_client,
                        width = 170.dp
                    ) {
                        viewModel.navigateToAddNewItemToNewInvoice()
                    }
                }
            }
            AnimatedVisibility(
                state.addNewInvoiceState.visible && state.addNewInvoiceState.addNewItemVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.code,
                        onValueChange = viewModel::onChangeCodeInNewItemValue,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.searchProductWithBarcode() }) {
                                Icon(painterResource(R.drawable.barcode_1), "")
                            }
                        },
                        label = {
                            Text(
                                "كود المنتج",
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
                    CustomDropDownMenu(
                        label = "اسم المنتج",
                        options = state.products.map { Pair(it.id.toLongOrNull() ?: -1L, it.name) },
                        selectedOption = Pair(
                            state.addNewInvoiceState.newInvoiceValues.id.toLongOrNull() ?: -1L,
                            state.addNewInvoiceState.newInvoiceValues.name
                        ),
                        onClickOption = {
                            viewModel.searchProductUsingId(it.first)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.qty,
                        onValueChange = viewModel::onChangeQtyInNewItemValue,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                "الكمية",
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = state.addNewInvoiceState.newInvoiceValues.priceForBuy,
                            onValueChange = viewModel::onChangePriceForBuyInNewItemValue,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (isVisiblePriceForBuy) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isVisiblePriceForBuy)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                IconButton(onClick = {
                                    isVisiblePriceForBuy = !isVisiblePriceForBuy
                                }) {
                                    Icon(imageVector = image, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            label = {
                                Text(
                                    "سعر الشراء",
                                    color = Color(0xffBFBFBF),
                                    textAlign = TextAlign.Start,
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
                            minLines = 1,
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = state.addNewInvoiceState.newInvoiceValues.priceForSale,
                            onValueChange = viewModel::onChangePriceForSaleInNewItemValue,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (isVisiblePriceForSale) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isVisiblePriceForSale)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                IconButton(onClick = {
                                    isVisiblePriceForSale = !isVisiblePriceForSale
                                }) {
                                    Icon(imageVector = image, contentDescription = null)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            label = {
                                Text(
                                    "سعر البيع",
                                    color = Color(0xffBFBFBF),
                                    textAlign = TextAlign.Start,
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
                            minLines = 1,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    OutlinedTextField(
                        value = state.addNewInvoiceState.newInvoiceValues.orderLimit,
                        onValueChange = viewModel::onChangeOrderLimitInNewItemValue,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "حد الطلب",
                                color = Color(0xffBFBFBF),
                                textAlign = TextAlign.Start,
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
                        minLines = 1,
                        shape = RoundedCornerShape(10.dp)
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
                        if (state.addNewInvoiceState.newInvoiceValues.isDoneLoading) LoadingIndicator()
                        else Text(
                            "اضافة المنتج",
                            color = Color.White,
                            style = Theme.typography.headerPhone.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600
                            )
                        )
                    }
                }
            }

        }
    }

    if (state.isSuccessSaving) {
        InvoiceSuccessDialog(
            onDismiss = viewModel::onClickDoneSaving,
            onShare = viewModel::onShareSuccessDialog,
            onDone = viewModel::onClickDoneSaving
        )
    }
    BackHandler {
        viewModel.navigateBackToDetails()
    }
}


@Composable
@Preview
fun InvoiceSuccessDialog(
    onDismiss: () -> Unit = {},
    onShare: () -> Unit = {},
    onDone: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_save_),
                    contentDescription = null,
                    tint = Color(0xff515151),
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "تم اضافة الفاتورة بنجاح",
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = Theme.typography.headerPhone
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = onShare,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "مشاركة",
                            color = Color.White,
                            style = Theme.typography.headerPhone.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = onDone,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF21C063)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            "انهاء", color = Color.White, style = Theme.typography.headerPhone.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
            }
        }
    }
}
