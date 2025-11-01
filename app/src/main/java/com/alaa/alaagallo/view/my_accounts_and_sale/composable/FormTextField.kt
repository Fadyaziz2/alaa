package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun FormTextField(
    text: String,
    hint: String,
    modifier: Modifier,
    trailing: @Composable (() -> Unit)? = null,
    isSingleLine: Boolean = true,
    isNumber: Boolean = true,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = { s ->
            if (isNumber)
                onValueChange(s.filter { it.isDigit() })
            else
                onValueChange(s)
        },
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(hint, style = Theme.typography.formPlaceHolder)
        },
        trailingIcon = trailing,
        singleLine = isSingleLine,
        maxLines = if (isSingleLine) 1 else Int.MAX_VALUE,
        keyboardOptions = if (isNumber) KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Theme.colors.greyLabel.copy(0.5f),
            unfocusedBorderColor = Theme.colors.greyLabel.copy(0.5f),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Theme.colors.colorTextHint,
            unfocusedPlaceholderColor = Theme.colors.colorTextHint,
            disabledTextColor = Color.Black,
        )
    )
}