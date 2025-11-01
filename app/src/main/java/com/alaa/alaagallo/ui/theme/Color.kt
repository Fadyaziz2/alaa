package com.alaa.alaagallo.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val green: Color = Color(0xff6CB253),
    val greenButton: Color = Color(0xff00BA2F),
    val orange: Color = Color(0xffFF8E5A),
    val yellow: Color = Color(0xffFFC801),
    val greyTableHeader: Color = Color(0xffEDEDED),
    val greySelectedItem: Color = Color(0xffEDEDED),
    val greyDivider: Color = Color(0xffD9D9D9),
    val greyBorder: Color = Color(0xff515151).copy(0.2f),
    val greyBorder2: Color = Color(0xff515151),
    val greyLabel: Color = Color(0xffBFBFBF),
    val red: Color = Color(0xffFD0808),
    val blueRadioButton: Color = Color(0xff389BE6),
    val colorTextHint: Color = Color(0xffBFBFBF),
    val purple300: Color,
)


val LightColors = Colors(
    purple300 = Color(0xFFB891F3),
)

val DarkColor = Colors(
    purple300 = Color(0xFFB891F3),
)