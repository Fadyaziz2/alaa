package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.inventory

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.alaa.Constants.INVENTORY_PDF_URL
import com.alaa.Constants.ORDER_LIMIT_PDF_URL
import com.alaa.Constants.OUT_OF_STOCK_PDF_URL
import com.alaa.Constants.RETURN_PDF_URL
import com.alaa.alaagallo.view.home.Home

@Composable
fun InventoryScreen(viewModel: InventoryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    val pdfUrl = when (state.selectedInventoryItem) {
        MenuItems.INVENTORY -> INVENTORY_PDF_URL
        MenuItems.ORDERLIMIT -> ORDER_LIMIT_PDF_URL
        MenuItems.RETURNS -> RETURN_PDF_URL
        MenuItems.SOLDOUT -> OUT_OF_STOCK_PDF_URL
    }



    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is InventoryUiEffect.ShowToastError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is InventoryUiEffect.ShowToastSuccessAddingCustomer -> {
                Toast.makeText(context, "تم اضافة المنتج بنجاح", Toast.LENGTH_SHORT).show()
            }

            is InventoryUiEffect.ShowToastSuccessEditingCustomer -> {
                Toast.makeText(context, "تم تعديل المنتج بنجاح", Toast.LENGTH_SHORT).show()
            }

            is InventoryUiEffect.NavigateBack -> {
                navController.popBackStack()
            }

            else -> TODO()
        }
    }
    InventoryContent(
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

enum class MenuItems(val title: String, val icon: Int) {
    INVENTORY("قائمة الجرد", R.drawable.inventory_icon),
    ORDERLIMIT("قائمة حد الطلب", R.drawable.orderlimit),
    RETURNS("قائمة المرتجع", R.drawable.returns),
    SOLDOUT("نافذ", R.drawable.soldout)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun InventoryContent(
    state: InventoryUiState, viewModel: InventoryViewModel,
    onClickPdf: (Int) -> Unit,
    onClickPrinter: (Int) -> Unit,
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                            Spacer(Modifier.height(16.dp))
                            MenuItems.entries.forEach {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .padding(horizontal = 8.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(
                                            if (state.selectedInventoryItem == it) 1.dp else 0.dp,
                                            if (state.selectedInventoryItem == it) Color(0xffFFC801) else Color.Transparent,
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            viewModel.onChangeSelectedMenuItem(it)

                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(it.icon),
                                            modifier = Modifier.size(24.dp),
                                            contentDescription = ""
                                        )
                                        Text(
                                            text = it.title,
                                            style = Theme.typography.headerMainTitle,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        if (state.selectedInventoryItem == it)
                                            Box(
                                                modifier = Modifier
                                                    .size(
                                                        height = 32.dp,
                                                        width = 6.dp
                                                    )
                                                    .background(
                                                        Color(0xffFFC801),
                                                        RoundedCornerShape(20.dp)
                                                    )
                                            )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            drawerState = drawerState
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                SalesScaffold(
                    title = if (state.isAddingNew) "اضافة منتج للمخزن" else if (state.isAddingReturn) "اضافة منتج مرتجع" else when (state.selectedInventoryItem) {
                        MenuItems.INVENTORY -> "المخزن"
                        MenuItems.ORDERLIMIT -> "المخزن | حد الطلب"
                        MenuItems.RETURNS -> "المخزن | المرتجع"
                        MenuItems.SOLDOUT -> "المخزن | نافذ"
                    },
                    navBack = { viewModel.navigateBackToDetails() },
                    actions = {
                        if (!state.isAddingNew && !state.isAddingReturn) {
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
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                ) { paddingValues ->
                    LoadingContent(state.isLoading) {
                        AnimatedVisibility(state.isAddingNew) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = paddingValues.calculateTopPadding())
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (state.isEdit) {
                                    OutlinedTextField(
                                        value = state.newItemValues.code,
                                        onValueChange = viewModel::onChangeCodeInNewItemValue,
                                        modifier = Modifier.fillMaxWidth(),
                                        readOnly = true,
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
                                }
                                OutlinedTextField(
                                    value = state.newItemValues.name,
                                    onValueChange = viewModel::onChangeNameInNewItemValue,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(
                                            "اسم المنتج",
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
                                    value = state.newItemValues.qty,
                                    onValueChange = viewModel::onChangeQtyInNewItemValue,
                                    modifier = Modifier.fillMaxWidth(),
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
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedTextField(
                                        value = state.newItemValues.priceForBuy,
                                        onValueChange = viewModel::onChangePriceForBuyInNewItemValue,
                                        modifier = Modifier.weight(1f),
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
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    OutlinedTextField(
                                        value = state.newItemValues.priceForSale,
                                        onValueChange = viewModel::onChangePriceForSaleInNewItemValue,
                                        modifier = Modifier.weight(1f),
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
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }
                                OutlinedTextField(
                                    value = state.newItemValues.orderLimit,
                                    onValueChange = viewModel::onChangeOrderLimitInNewItemValue,
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
                                    shape = RoundedCornerShape(10.dp)
                                )
                                Spacer(Modifier.height(50.dp))

                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color(
                                            0xff00BA2F
                                        )
                                    ),
                                    onClick = viewModel::onClickDone
                                ) {
                                    if (state.newItemValues.isDoneLoading)
                                        LoadingIndicator()
                                    else
                                        Text(
                                            if (state.isEdit) "تعديل المنتج" else "اضافة المنتج",
                                            color = Color.White,
                                            style = Theme.typography.headerPhone.copy(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.W600
                                            )
                                        )
                                }
                            }
                        }
                        AnimatedVisibility(state.isAddingReturn) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = paddingValues.calculateTopPadding())
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = state.newItemValues.code,
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
                                    options = state.productItemsTypes.inventoryItems.map {
                                        Pair(
                                            it.id.toLongOrNull() ?: -1L, it.name
                                        )
                                    },
                                    selectedOption = Pair(
                                        state.newItemValues.id.toLongOrNull() ?: -1L,
                                        state.newItemValues.name
                                    ),
                                    onClickOption = {
                                        viewModel.searchProductUsingId(it.first)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                OutlinedTextField(
                                    value = state.newItemValues.qty,
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
                                        value = state.newItemValues.priceForBuy,
                                        onValueChange = viewModel::onChangePriceForBuyInNewItemValue,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        readOnly = true,
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
                                    OutlinedTextField(
                                        value = state.newItemValues.priceForSale,
                                        onValueChange = viewModel::onChangePriceForSaleInNewItemValue,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        readOnly = true,
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
                                    value = state.newItemValues.orderLimit,
                                    onValueChange = viewModel::onChangeOrderLimitInNewItemValue,
                                    modifier = Modifier.fillMaxWidth(),
                                    readOnly = true,
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
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color(
                                            0xff00BA2F
                                        )
                                    ),
                                    onClick = viewModel::onClickDoneOnReturn
                                ) {
                                    if (state.newItemValues.isDoneLoading)
                                        LoadingIndicator()
                                    else
                                        Text(
                                            if (state.isEdit) "تعديل المنتج" else "اضافة المنتج",
                                            color = Color.White,
                                            style = Theme.typography.headerPhone.copy(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.W600
                                            )
                                        )
                                }
                            }
                        }

                        AnimatedVisibility(state.selectedInventoryItem == MenuItems.INVENTORY && !state.isAddingNew && !state.isAddingReturn) {
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
                                                label = "بحث في المخزن",
                                                onValueChange = viewModel::onChangeSearchValue
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp)
                                                    .background(Color(0xffA8F2BA)),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                listOf(
                                                    "اسم المنتج" to 2f,
                                                    "الكمية" to 1.5f,
                                                    "حد الطلب" to 1.5f,
                                                    "سعر البيع" to 2f,
                                                    "سعر الشراء" to 2f,
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
                                    items(state.productItemsTypes.inventoryItems.filter {
                                        it.name.contains(state.searchValue)
                                    }.size)
                                    { index ->
                                        val currentItem =
                                            state.productItemsTypes.inventoryItems.filter {
                                                it.name.contains(state.searchValue)
                                            }[index]
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .clickable {
                                                    viewModel.onClickItem(currentItem)
                                                }
                                                .background(Color.White),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            listOf(
                                                currentItem.name to 2f,
                                                currentItem.stock.toString() to 1.5f,
                                                currentItem.minimumStock.toString() to 1.5f,
                                                currentItem.sellingPrice.toString() to 2f,
                                                currentItem.buyPrice.toString() to 2f,
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
                                            text = "اجمال سعر الشراء : ${if (state.productItemsTypes.inventoryItems.isNotEmpty()) state.productItemsTypes.inventoryItems.sumOf { (it.buyPrice * it.stock) } else 0}",
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
                                    text = "اضافة منتج للمخزن",
                                    icon = R.drawable.ic_add_invoice,
                                    width = 170.dp
                                ) {
                                    viewModel.navigateToAddNew()
                                }
                            }
                        }
                        AnimatedVisibility(state.selectedInventoryItem == MenuItems.ORDERLIMIT && !state.isAddingNew && !state.isAddingReturn) {
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
                                                label = "بحث في حد الطلب",
                                                onValueChange = viewModel::onChangeSearchValue
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp)
                                                    .background(Color(0xffFFC8C4)),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                listOf(
                                                    "اسم المنتج" to 1.5f,
                                                    "حد الطلب" to 1f,
                                                    "الكمية المطلوبة" to 1.2f,
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
                                    items(state.productItemsTypes.orderLimitItem.filter {
                                        it.name.contains(state.searchValue)
                                    }.size) { index ->
                                        val currentItem =
                                            state.productItemsTypes.orderLimitItem.filter {
                                                it.name.contains(state.searchValue)
                                            }[index]
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .background(Color.White),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            listOf(
                                                currentItem.name to 1.5f,
                                                currentItem.minimumStock.toString() to 1f,
                                                //currentItem.stock.toString() to 1.2f,
                                                "" to 1.2f,
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
                                            text = "اجمال منتجات حد الطلب : ${state.productItemsTypes.orderLimitItem.size}",
                                            style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                            modifier = Modifier,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(state.selectedInventoryItem == MenuItems.RETURNS && !state.isAddingNew && !state.isAddingReturn) {
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
                                                label = "بحث في المرتجع",
                                                onValueChange = viewModel::onChangeSearchValue
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp)
                                                    .background(Color(0xffFFEF99)),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                listOf(
                                                    "اسم المنتج" to 2f,
                                                    "الكمية" to 1f,
                                                    "سعر البيع" to 2f,
                                                    "سعر الشراء" to 2f,
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
                                    items(state.productItemsTypes.returnItems.filter {
                                        it.product.name.contains(state.searchValue)
                                    }.size) { index ->
                                        val currentItem =
                                            state.productItemsTypes.returnItems.filter {
                                                it.product.name.contains(state.searchValue)
                                            }[index]
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .clickable {
                                                    viewModel.onClickItem(
                                                        currentItem.product,
                                                        true,
                                                        currentItem.id
                                                    )
                                                }
                                                .background(Color.White),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            listOf(
                                                currentItem.product.name to 2f,
                                                currentItem.product.stock.toString() to 1f,
                                                currentItem.product.sellingPrice.toString() to 2f,
                                                currentItem.product.buyPrice.toString() to 2f,
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
                                            text = "الاجمالي المرتجع الشهري : ${state.productItemsTypes.returnItems.size}",
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
                                    text = "اضافة مرتجع",
                                    icon = R.drawable.ic_add_invoice,
                                    //width = 170.dp
                                ) {
                                    viewModel.navigateToAddNewReturn()
                                }
                            }
                        }
                        AnimatedVisibility(state.selectedInventoryItem == MenuItems.SOLDOUT && !state.isAddingNew && !state.isAddingReturn) {
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
                                                label = "بحث في النافذ",
                                                onValueChange = viewModel::onChangeSearchValue
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp)
                                                    .background(Color(0xffD9D9D9)),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                listOf(
                                                    "اسم المنتج" to 2f,
                                                    "سعر البيع" to 1f,
                                                    "سعر الشراء" to 1f,
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
                                    items(state.productItemsTypes.soldOutItems.filter {
                                        it.name.contains(state.searchValue)
                                    }.size) { index ->
                                        val currentItem =
                                            state.productItemsTypes.soldOutItems.filter {
                                                it.name.contains(state.searchValue)
                                            }[index]
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .background(Color.White),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            listOf(
                                                currentItem.name to 2f,
                                                currentItem.sellingPrice.toString() to 1f,
                                                currentItem.buyPrice.toString() to 1f,
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
                                            text = "الاجمالي النافذ الشهري  : ${state.productItemsTypes.soldOutItems.size}",
                                            style = Theme.typography.headerPhone.copy(textAlign = TextAlign.Center),
                                            modifier = Modifier,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



