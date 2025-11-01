package com.alaa.alaagallo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun headerMainTitle(): TextStyle {
    return TextStyle(
        fontSize = 16.sp,
        lineHeight = 29.98.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
    )
}
@Composable
fun headerPhone(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun footerAddOption(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 26.24.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
    )
}
@Composable
fun footerText(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 26.24.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Medium,
    )
}
@Composable
fun tableHeader(): TextStyle {
    return TextStyle(
        fontSize = 10.sp,
        lineHeight = 18.74.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}
@Composable
fun tableContent(): TextStyle {
    return TextStyle(
        fontSize = 12.sp,
        lineHeight = 12.1.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
    )
}
@Composable
fun formTitle(): TextStyle {
    return TextStyle(
        fontSize = 10.sp,
        lineHeight = 26.24.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
    )
}
@Composable
fun navigationDrawer(): TextStyle {
    return TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.49.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )
}
@Composable
fun formPlaceHolder(): TextStyle {
    return TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.49.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
    )
}
@Composable
fun formPositiveButton(): TextStyle {
    return TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.49.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
    )
}
@Composable
fun formNegativeButton(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 26.24.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
    )
}
@Composable
fun titleDialog(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 26.24.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
}

@Composable
fun formContent(): TextStyle {
    return TextStyle(
        fontSize = 10.sp,
        lineHeight = 18.74.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
    )
}
@Composable
fun body(): TextStyle {
    return TextStyle(
        fontSize = 16.sp,
        lineHeight = 12.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
    )
}
