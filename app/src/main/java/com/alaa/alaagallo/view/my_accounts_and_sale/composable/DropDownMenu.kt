package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuItem(
    text: String = "",
    selectedPos: Int = -1,
    height: Int = 150,
    backGroundMenuColor: Color = Color.White,
    backGroundContainerColor: Color = Color.White,
    hint: String,
    list: List<String> = listOf("Item 1", "Item 2", "Item 3"),
    onItemSelected: (Int, String) -> Unit,
) {
    var expand by remember {
        mutableStateOf(false)
    }

    val scrollState = rememberLazyListState()

    // Update selected item in the first time
    LaunchedEffect(expand) {
        if (expand && selectedPos >= 0 && selectedPos < list.size) {
            scrollState.scrollToItem(selectedPos)
        }
    }

    ExposedDropdownMenuBox(
        expanded = expand,
        onExpandedChange = { expand = !expand },
    ) {
        TextField(
            value = text,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                focusedContainerColor = backGroundContainerColor,
                unfocusedContainerColor = backGroundContainerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            readOnly = true,
            placeholder = {
                Text(
                    text = hint,
                    style = Theme.typography.body,
                    color = Color.Black
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
        )
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false },
            modifier = Modifier
                .background(
                    backGroundMenuColor
                )
                .fillMaxWidth(0.85f)
                .heightIn(max = 300.dp)

        ) {
            LazyColumn(
                modifier = Modifier.size(width = 300.dp, height = height.dp),
                state = scrollState
            ) {
                itemsIndexed(list) { index, item ->
                    DropdownMenuItem(
                        contentPadding = PaddingValues(12.dp),
                        colors = MenuDefaults.itemColors(
                            textColor = if (selectedPos == index) Color.Black else Color.Gray,
                        ),
                        text = {
                            Text(
                                text = item,
                            )
                        }, onClick = {
                            expand = false
                            onItemSelected(index, item)
                        })
                }
            }
        }
    }
}