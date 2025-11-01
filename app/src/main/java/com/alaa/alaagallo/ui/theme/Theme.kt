package com.alaa.alaagallo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf


val localColorScheme = staticCompositionLocalOf { LightColors }
private val localTypography = staticCompositionLocalOf { Typography() }
@Composable
fun AlaaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColor else LightColors

    val typography = Typography(
        formTitle = formTitle(),
        footerText = footerText(),
        formNegativeButton = formNegativeButton(),
        formPositiveButton = formPositiveButton(),
        formPlaceHolder = formPlaceHolder(),
        footerAddOption = footerAddOption(),
        headerMainTitle = headerMainTitle(),
        headerPhone = headerPhone(),
        tableContent = tableContent(),
        tableHeader = tableHeader(),
        navigationDrawer = navigationDrawer(),
        titleDialog = titleDialog(),
        formContent = formContent(),
        body = body(),
    )

    CompositionLocalProvider(
        localColorScheme provides colorScheme,
        localTypography provides typography,
    )
    {
        content()
    }
}

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = localColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = localTypography.current

//    val radius: Radius
//        @Composable
//        @ReadOnlyComposable
//        get() = localRadius.current
}