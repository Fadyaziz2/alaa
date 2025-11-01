package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun NegativeDialogButton(
    modifier: Modifier = Modifier.size(100.dp, 48.dp),
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    color: Color = Color.White,
    textColor: Color = Theme.colors.red,
    text:String="الغاء",
    onClick: () -> Unit
) {
    Button(
        modifier = modifier, onClick = {
            if(!isLoading)
                onClick()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        enabled = isEnabled
    ) {
        Text(text = text, color = textColor, style = Theme.typography.formNegativeButton)
    }
}