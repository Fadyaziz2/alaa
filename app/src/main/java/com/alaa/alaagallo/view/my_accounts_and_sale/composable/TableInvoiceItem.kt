package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.alaa.domain.entity.Invoice

@Composable
fun TableInvoiceItem(invoice: Invoice, onClickItem: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    onClickItem()
                }
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TableTextItem(modifier = Modifier.weight(2f), text = invoice.createdAt)
        Spacer(Modifier.width(8.dp))
        TableTextItem(modifier = Modifier.weight(1f), text = invoice.amount.toString())
        Spacer(Modifier.width(8.dp))
        TableTextItem(modifier = Modifier.weight(1f), text = invoice.moreInfo)
    }
}