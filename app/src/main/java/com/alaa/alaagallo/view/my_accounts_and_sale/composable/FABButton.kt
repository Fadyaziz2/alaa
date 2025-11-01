package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun FABButton(
    modifier: Modifier,
    text: String,
    icon: Int,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier
            .padding(start = 20.dp, bottom = 50.dp)
            .size(140.dp, 42.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Theme.colors.orange)
            .clickable {
                if (!isLoading)
                    onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(isLoading, label = "") {
            if (it) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator(color = Color.White)
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = ""
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text,
                        style = Theme.typography.footerAddOption,
                        color = Color.White
                    )
                }
            }
        }

    }
}