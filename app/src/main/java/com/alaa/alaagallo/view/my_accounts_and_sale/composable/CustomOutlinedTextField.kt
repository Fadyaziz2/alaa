package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun CustomOutlinedTextField(
    label: String,
    cornerRadius: Dp,
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    borderColor: Color = Theme.colors.greyBorder,
    labelColor: Color = Theme.colors.greyLabel
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = Theme.typography.formPlaceHolder) },
        shape = RoundedCornerShape(cornerRadius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = borderColor,
            cursorColor = Color.Black ,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = labelColor,
            errorBorderColor = Theme.colors.red.copy(0.7f),
            errorCursorColor = Theme.colors.red.copy(0.7f),
            errorLabelColor = Theme.colors.red.copy(0.7f)
        ),
        singleLine = true,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
    )
}