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
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts.User

@Composable
fun TableContentItem(user: User, onClickUser: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    onClickUser()
                }
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TableTextItem(modifier = Modifier.weight(1f), text = user.name)
        Spacer(Modifier.width(8.dp))
        TableTextItem(modifier = Modifier.weight(1f), text = user.mobile)
        Spacer(Modifier.width(8.dp))
        TableTextItem(modifier = Modifier.weight(1f), text = user.accountInvoicesCount.toString())
        Spacer(Modifier.width(8.dp))
        TableTextItem(modifier = Modifier.weight(0.5f), text = user.accountTotalInvoice.toString())
    }
}