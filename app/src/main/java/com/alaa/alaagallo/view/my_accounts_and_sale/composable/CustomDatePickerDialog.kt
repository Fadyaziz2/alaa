package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alaa.alaagallo.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDismiss: () -> Unit = {},
    onDateSelected: (Long?) -> Unit = {},
    selectedDate: Long? = null,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )
    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        ),
        modifier = Modifier.scale(0.8f),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(
                    text = "تاكيد",
                    style = Theme.typography.titleDialog.copy(fontSize = 18.sp),
                    color = Color.Blue
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(
                    text = "الغاء",
                    style = Theme.typography.titleDialog.copy(fontSize = 18.sp),
                    color = Color.Blue
                )
            }
        }
    ) {
        DatePicker(
            modifier = Modifier.padding(top = 14.dp),
            state = datePickerState,
            showModeToggle = false,
            title = null,
            colors = DatePickerDefaults.colors(
                headlineContentColor = Color.Blue,
                navigationContentColor = Color.Blue,
                selectedYearContainerColor = Color.Blue,
                selectedDayContainerColor = Color.Blue,
                todayDateBorderColor = Color.Blue,
                dayContentColor = Color.Black,
                todayContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                yearContentColor = Color.Black,
                containerColor = Color.White,
            )
        )
    }
}