package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun SearchField(
    value: String,
    label: String = "بحث",
    onValueChange: (String) -> Unit,
    onClickFilter: () -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                "",
                modifier = Modifier.scale(scaleX = -1f, scaleY = 1f)
            )
        },
        label = {
            Text(
                label,
                style = Theme.typography.headerMainTitle.copy(
                    fontWeight = FontWeight.W400,
                    color = Color(0xffBFBFBF),
                    fontSize = 14.sp
                )
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onClickFilter
            ) {
                Icon(painter = painterResource(R.drawable.ic_filter), "", tint = Color(0xffFFC801))
            }
        },
        textStyle = Theme.typography.footerText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color(0xffBFBFBF)
        )
    )
}