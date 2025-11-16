package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.reports

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

@Composable
fun ReportsScreen() {
    val navController = LocalNavigationProvider.current
    val reportItems = remember { ReportType.entries.toList() }
    var isInvoiceDialogVisible by rememberSaveable { mutableStateOf(false) }
    var invoiceNumber by rememberSaveable { mutableStateOf("") }

    SalesScaffold(title = "التقارير") { paddingValues ->
        ReportsList(
            paddingValues = paddingValues,
            items = reportItems,
            onReportClick = { type ->
                if (type.requiresInvoiceNumber) {
                    isInvoiceDialogVisible = true
                } else {
                    navController.navigate(Screen.ReportDetails.createRoute(type))
                }
            }
        )
    }

    if (isInvoiceDialogVisible) {
        InvoiceReportDialog(
            invoiceNumber = invoiceNumber,
            onInvoiceChange = { invoiceNumber = it },
            onDismiss = { isInvoiceDialogVisible = false },
            onSearch = {
                val value = invoiceNumber.ifBlank { "123456789" }
                navController.navigate(
                    Screen.ReportDetails.createRoute(
                        type = ReportType.InvoiceProfit,
                        invoiceNumber = value
                    )
                )
                invoiceNumber = ""
                isInvoiceDialogVisible = false
            }
        )
    }
}

@Composable
private fun ReportsList(
    paddingValues: PaddingValues,
    items: List<ReportType>,
    onReportClick: (ReportType) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(items, key = { it.id }) { reportType ->
            ReportItemCard(reportType = reportType) {
                onReportClick(reportType)
            }
        }
    }
}

@Composable
private fun ReportItemCard(
    reportType: ReportType,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF6F6F8))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.yellow.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reports_icon),
                    contentDescription = null,
                    tint = Theme.colors.yellow,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = reportType.title,
                style = Theme.typography.footerAddOption.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.left_arrow),
            contentDescription = null,
            tint = Color(0xFF101010),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun InvoiceReportDialog(
    invoiceNumber: String,
    onInvoiceChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSearch: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.yellow.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reports_icon),
                    contentDescription = null,
                    tint = Theme.colors.yellow,
                    modifier = Modifier.size(32.dp)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "تقرير الارباح برقم الفاتورة",
                    style = Theme.typography.headerMainTitle,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ادخل رقم الفاتورة للبحث",
                    style = Theme.typography.footerText,
                    color = Theme.colors.colorTextHint,
                    textAlign = TextAlign.Center
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = invoiceNumber,
                onValueChange = onInvoiceChange,
                placeholder = {
                    Text(
                        text = "رقم الفاتورة",
                        style = Theme.typography.formPlaceHolder,
                        color = Theme.colors.colorTextHint
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Theme.colors.yellow,
                    unfocusedBorderColor = Theme.colors.greyBorder,
                    cursorColor = Theme.colors.yellow,
                )
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSearch,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.greenButton)
            ) {
                Text(
                    text = "بحث",
                    style = Theme.typography.footerAddOption,
                    color = Color.White
                )
            }
        }
    }
}
