package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts.CategoryUi

@Composable
fun NavigationDrawerCategories(
    isLoading: Boolean,
    isLoadingAddCategory: Boolean,
    isSucceedAddCategory: Boolean,
    isSelectedAll: Boolean,
    list: List<CategoryUi>,
    numOfAllUsers: Int,
    onClickDismiss: () -> Unit,
    onClickAllItem: () -> Unit,
    onClickItem: (Int) -> Unit,
    onPressLongItem: (CategoryUi) -> Unit,
    onClickNewCategory: (String) -> Unit
) {
    var addCategoryState by remember { mutableStateOf(false) }
    var textFieldAddCategoryError by remember { mutableStateOf(false) }
    var textFieldAddCategory by remember { mutableStateOf("") }
    LaunchedEffect(isSucceedAddCategory) {
        if (isSucceedAddCategory)
            addCategoryState = false
    }
    Box(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .background(Color.White),
    ) {
        AnimatedContent(isLoading, label = "") {
            if (it) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator()
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
//                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(Color.Black)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        onClickDismiss()
                                    }
                                }
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(
                            "التصنيفات",
                            style = Theme.typography.headerMainTitle,
                            color = Color.White,
                        )
                    }
                    Spacer(Modifier.height(30.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(bottom = 12.dp)
                    ) {
                        item {
                            NavigationDrawerItem(
                                CategoryUi(
                                    id = 0,
                                    name = "الكل",
                                    isSelected = isSelectedAll,
                                    numOfAccounts = numOfAllUsers,
                                ),
                                isAll = true
                            ) {
                                onClickAllItem()
                            }
                        }
                        itemsIndexed(list) { index, item ->
                            NavigationDrawerItem(item, onPressLongItem = {
                                onPressLongItem(item)
                            }) {
                                onClickItem(index)
                            }
                        }
                    }
                }
            }
        }

        FABButton(
            modifier = Modifier.align(Alignment.BottomStart),
            text = "تصنيف جديد",
            icon = R.drawable.ic_plus
        ) {
            addCategoryState = true
        }

        if (addCategoryState) {
            Dialog(
                onDismissRequest = { addCategoryState = false },
                properties = DialogProperties(dismissOnClickOutside = false)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(
                            top = 20.dp, start = 30.dp, end = 30.dp, bottom = 39.dp
                        ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("اسم التصنيف", style = Theme.typography.titleDialog)
                    Spacer(Modifier.height(20.dp))
                    HorizontalDivider(
                        thickness = 0.5.dp, color = Theme.colors.greyDivider
                    )
                    Spacer(Modifier.height(30.dp))
                    CustomOutlinedTextField(
                        value = textFieldAddCategory,
                        onValueChange = {
                            textFieldAddCategoryError = false
                            textFieldAddCategory = it
                        },
                        cornerRadius = 100.dp,
                        label = "ادخل اسم التصنيف",
                        isError = textFieldAddCategoryError
                    )
                    Spacer(Modifier.height(34.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        PositiveButton(
                            text = "حفظ التصنيف",
                            isLoading = isLoadingAddCategory
                        ) {
                            if (textFieldAddCategory.isEmpty()) textFieldAddCategoryError =
                                true
                            else {
                                onClickNewCategory(textFieldAddCategory)
                            }
                        }
                        Spacer(Modifier.width(5.dp))
                        NegativeDialogButton(isLoading = isLoadingAddCategory) {
                            addCategoryState = false
//                            menuState = true
                            textFieldAddCategory = ""
                        }
                    }
                }
            }
        }
    }
}