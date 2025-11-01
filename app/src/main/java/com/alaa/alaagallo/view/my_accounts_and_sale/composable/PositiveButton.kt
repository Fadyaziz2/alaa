package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun PositiveButton(
    text: String,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.size(100.dp, 48.dp),
    color: Color = Theme.colors.greenButton,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier, onClick = {
            if(!isLoading)
                onClick()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        AnimatedContent(isLoading, label = "", contentAlignment = Alignment.Center) {
            if (it) {
                Box(modifier, contentAlignment = Alignment.Center) {
                    LoadingIndicator(color = Color.White)
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = text,
                        color = Color.White,
                        style = Theme.typography.formNegativeButton,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}