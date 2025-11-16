package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.barcode

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.print.PrintHelper
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.BarcodeLabelBuilder
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.alaagallo.view.my_accounts_and_sale.base.EventHandler
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.CustomDropDownMenu
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.domain.entity.ProductEntity

@Composable
fun BarcodePrinterScreen(viewModel: BarcodePrinterViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val labelBuilder = remember { BarcodeLabelBuilder() }

    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is BarcodePrinterUiEffect.ShowToastRes -> {
                val text = context.getString(effect.messageRes, *effect.formatArgs.toTypedArray())
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
            is BarcodePrinterUiEffect.ShowToastMessage -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is BarcodePrinterUiEffect.Print -> {
                val bitmap = labelBuilder.buildForPrinting(
                    product = effect.product,
                    showPrice = effect.barcodeType.showPrice,
                    copies = effect.copies,
                    density = density,
                )
                if (bitmap == null) {
                    Toast.makeText(context, context.getString(R.string.barcode_generation_error), Toast.LENGTH_SHORT).show()
                } else {
                    val printHelper = PrintHelper(context)
                    printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
                    printHelper.printBitmap(
                        context.getString(R.string.barcode_print_job_title, effect.product.name),
                        bitmap,
                        PrintHelper.OnPrintFinishCallback {
                            bitmap.recycle()
                        }
                    )
                }
            }
        }
    }

    BarcodePrinterContent(
        state = state,
        onSelectProduct = viewModel::onSelectProduct,
        onBarcodeTypeChange = viewModel::onSelectBarcodeType,
        onQuantityChange = viewModel::onQuantityChanged,
        onPrintClick = viewModel::onPrintClick,
        onReloadProducts = viewModel::refreshProducts,
        labelBuilder = labelBuilder,
        density = density,
    )
}

@Composable
private fun BarcodePrinterContent(
    state: BarcodePrinterUiState,
    onSelectProduct: (ProductEntity) -> Unit,
    onBarcodeTypeChange: (BarcodeType) -> Unit,
    onQuantityChange: (String) -> Unit,
    onPrintClick: () -> Unit,
    onReloadProducts: () -> Unit,
    labelBuilder: BarcodeLabelBuilder,
    density: Float,
) {
    val previewBitmap = remember(state.selectedProduct, state.barcodeType, density) {
        state.selectedProduct?.let {
            labelBuilder.buildPreview(it, state.barcodeType.showPrice, density)
        }
    }
    SalesScaffold(title = stringResource(R.string.barcode_print_title)) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BarcodePreview(previewBitmap)
                    if (state.products.isEmpty()) {
                        EmptyProductsSection(state.errorState, onReloadProducts)
                    } else {
                        ProductDropDown(
                            products = state.products,
                            selectedProduct = state.selectedProduct,
                            onSelected = onSelectProduct,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    BarcodeTypeDropdown(
                        selectedType = state.barcodeType,
                        onBarcodeTypeChange = onBarcodeTypeChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.quantity,
                        onValueChange = onQuantityChange,
                        label = {
                            Text(
                                text = stringResource(R.string.barcode_quantity_label),
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(fontSize = 13.sp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onPrintClick,
                        enabled = state.selectedProduct != null && !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffFFC801),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.barcode_print_button),
                            style = Theme.typography.footerAddOption.copy(fontSize = 16.sp, color = Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BarcodePreview(previewBitmap: Bitmap?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xffF7F7F7))
            .border(1.dp, Color(0xffE0E0E0), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (previewBitmap != null) {
            Image(
                bitmap = previewBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = stringResource(R.string.barcode_preview_placeholder),
                style = Theme.typography.headerPhone,
                color = Color(0xff8D8D8D),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun EmptyProductsSection(errorState: ErrorUiState?, onReloadProducts: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xffFFF4D4))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val message = when (errorState) {
            is ErrorUiState.NoInternet -> errorState.message
            is ErrorUiState.Server -> errorState.message
            null -> stringResource(R.string.barcode_empty_products)
        }
        Text(text = message, style = Theme.typography.headerPhone, color = Color(0xff7A5C00))
        TextButton(onClick = onReloadProducts) {
            Text(text = stringResource(R.string.barcode_reload_products), color = Color(0xff7A5C00))
        }
    }
}

@Composable
private fun BarcodeTypeDropdown(
    selectedType: BarcodeType,
    onBarcodeTypeChange: (BarcodeType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = BarcodeType.entries.associateWith { stringResource(it.titleRes) }
    CustomDropDownMenu(
        label = stringResource(R.string.barcode_select_type),
        options = options.values.toList(),
        selectedOption = options[selectedType].orEmpty(),
        onClickOption = { label ->
            options.entries.firstOrNull { it.value == label }?.key?.let(onBarcodeTypeChange)
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDropDown(
    products: List<ProductEntity>,
    selectedProduct: ProductEntity?,
    onSelected: (ProductEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (products.isNotEmpty()) {
                expanded = !expanded
            }
        }
    ) {
        OutlinedTextField(
            value = selectedProduct?.name ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = products.isNotEmpty(),
            modifier = modifier.menuAnchor(),
            label = {
                Text(
                    text = stringResource(R.string.barcode_select_product),
                    color = Color(0xffBFBFBF),
                    style = Theme.typography.headerPhone.copy(fontSize = 13.sp)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xff515151),
                focusedBorderColor = Color(0xff515151)
            ),
            shape = RoundedCornerShape(10.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            products.forEach { product ->
                DropdownMenuItem(
                    text = { Text(text = product.name) },
                    onClick = {
                        onSelected(product)
                        expanded = false
                    }
                )
            }
        }
    }
}
