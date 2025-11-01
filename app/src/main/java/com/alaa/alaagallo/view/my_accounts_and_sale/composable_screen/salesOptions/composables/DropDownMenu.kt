package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alaa.alaagallo.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onClickOption: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .menuAnchor(),
            label = {
                Text(
                    label, color = Color(0xffBFBFBF), style = Theme.typography.headerPhone.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xff515151),
                focusedBorderColor = Color(0xff515151)
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onClickOption(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    label: String,
    options: List<Pair<Long, String>>,
    selectedOption: Pair<Long, String>,
    onClickOption: (Pair<Long, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedOption.second,
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .menuAnchor(),
            label = {
                Text(
                    label, color = Color(0xffBFBFBF), style = Theme.typography.headerPhone.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xff515151),
                focusedBorderColor = Color(0xff515151)
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.second) },
                    onClick = {
                        onClickOption(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }

    }
}