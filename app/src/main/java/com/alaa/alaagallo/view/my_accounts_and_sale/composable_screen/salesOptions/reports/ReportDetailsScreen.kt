package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold

@Composable
fun ReportDetailsScreen(
    reportType: ReportType,
    invoiceNumber: String? = null,
) {
    SalesScaffold(title = reportType.title) { paddingValues ->
        ReportDetailsContent(
            paddingValues = paddingValues,
            reportType = reportType,
            invoiceNumber = invoiceNumber,
        )
    }
}

@Composable
private fun ReportDetailsContent(
    paddingValues: PaddingValues,
    reportType: ReportType,
    invoiceNumber: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ReportDateRangeCard()
        if (reportType.summaryRows.isEmpty()) {
            ReportSingleSummaryCard(
                title = reportType.summaryTitle,
                amount = reportType.amountText,
                invoiceNumber = invoiceNumber.takeIf { reportType.requiresInvoiceNumber }
            )
        } else {
            ReportSummaryListCard(
                title = reportType.summaryTitle,
                rows = reportType.summaryRows
            )
        }
    }
}

@Composable
private fun ReportDateRangeCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF4F4F4))
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "من 23/3/2025 الى 22/5/2025",
                style = Theme.typography.footerAddOption,
                color = Color.Black,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "يمكنك اختيار الفترة لاحقًا",
                style = Theme.typography.formPlaceHolder,
                color = Theme.colors.colorTextHint,
                textAlign = TextAlign.End
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            tint = Theme.colors.yellow,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun ReportSingleSummaryCard(
    title: String,
    amount: String,
    invoiceNumber: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .border(1.dp, Theme.colors.greyBorder.copy(alpha = 0.4f), RoundedCornerShape(28.dp))
            .padding(horizontal = 24.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReportIconHeader()
        ReportTitle(title = title, invoiceNumber = invoiceNumber)
        AmountHighlight(amount = amount)
    }
}

@Composable
private fun ReportSummaryListCard(
    title: String,
    rows: List<ReportSummaryRow>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .border(1.dp, Theme.colors.greyBorder.copy(alpha = 0.4f), RoundedCornerShape(28.dp))
            .padding(horizontal = 24.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReportIconHeader()
        ReportTitle(title = title, invoiceNumber = null)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF7F8F9))
        ) {
            rows.forEachIndexed { index, row ->
                ReportSummaryRowItem(row = row)
                if (index != rows.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = Theme.colors.greyDivider.copy(alpha = 0.7f),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportIconHeader() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Theme.colors.yellow.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.reports_icon),
            contentDescription = null,
            tint = Theme.colors.yellow,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun ReportTitle(
    title: String,
    invoiceNumber: String?,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = Theme.typography.footerAddOption.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        if (!invoiceNumber.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "($invoiceNumber)",
                style = Theme.typography.footerText,
                color = Theme.colors.colorTextHint,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AmountHighlight(amount: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFEFFAF3))
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amount,
            style = Theme.typography.footerAddOption.copy(fontWeight = FontWeight.Bold),
            color = Theme.colors.greenButton
        )
    }
}

@Composable
private fun ReportSummaryRowItem(row: ReportSummaryRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = row.value,
            style = Theme.typography.footerAddOption.copy(fontWeight = FontWeight.Bold),
            color = Theme.colors.greenButton
        )
        Text(
            text = row.label,
            style = Theme.typography.footerText,
            color = Color.Black
        )
    }
}
