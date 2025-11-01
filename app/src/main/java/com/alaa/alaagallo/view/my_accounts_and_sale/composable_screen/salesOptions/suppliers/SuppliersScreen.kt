package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.suppliers

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.base.EventHandler
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.AddButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SearchField
import kotlinx.coroutines.launch


@Composable
fun SuppliersScreen(viewModel: SuppliersViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    val navController = LocalNavigationProvider.current

    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is SuppliersUiEffect.ShowToastError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            is SuppliersUiEffect.ShowToastSuccessAddingSupplier -> {
                Toast.makeText(context, "تم اضافة العميل بنجاح", Toast.LENGTH_SHORT).show()
            }

            is SuppliersUiEffect.ShowToastSuccessEditingSupplier -> {
                Toast.makeText(context, "تم تعديل العميل بنجاح", Toast.LENGTH_SHORT).show()
            }

            is SuppliersUiEffect.NavigateBack -> {
                navController.popBackStack()
            }

            else -> TODO()
        }
    }
    SuppliersContent(state, viewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SuppliersContent(state: SuppliersUiState, viewModel: SuppliersViewModel) {
    val scope = rememberCoroutineScope()
    SalesScaffold(
        title = if (state.isAddingNew) "اضافة مورد" else "المورديين",
        navBack = {
            viewModel.navigateBackToDetails()
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        } else {
            AnimatedVisibility(state.isAddingNew) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onChangeNameValue,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "اسم المورد",
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = viewModel::onChangePhoneValue,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { Icon(Icons.Default.AccountBox, "") },
                        label = {
                            Text(
                                "رقم الهاتف",
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = state.costs,
                        onValueChange = viewModel::onChangeCostsValue,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "اجمالي الفاتورة",
                                color = Color(0xffBFBFBF),
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = state.details,
                        onValueChange = viewModel::onChangeDetailsValue,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                "ملاحظات",
                                color = Color(0xffBFBFBF),
                                textAlign = TextAlign.Start,
                                style = Theme.typography.headerPhone.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400
                                )
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xff515151),
                            focusedBorderColor = Color(0xff515151)
                        ),
                        minLines = 4,
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(Modifier.height(50.dp))

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.textButtonColors(containerColor = Color(0xff00BA2F)),
                        onClick = viewModel::onClickDone
                    ) {
                        if (state.isDoneLoading)
                            LoadingIndicator()
                        else
                            Text(if (state.isEdit) "تعديل" else "حفظ", color = Color.White, style = Theme.typography.headerPhone.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600
                            ))
                    }
                }
            }
            AnimatedVisibility(!state.isAddingNew) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                ) {
                    LazyColumn(
                    ) {
                        stickyHeader {
                            Column(modifier = Modifier.background(Color.White)) {
                                SearchField(
                                    value = state.searchValue,
                                    onValueChange = viewModel::onChangeSearchValue
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(Color(0xffEDEDED)),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        "الرقم" to 1f,
                                        "اسم مورد" to 2f,
                                        "رقم الهاتف" to 3f,
                                        "اجمالي الفواتير" to 2f,
                                    ).forEach { pair: Pair<String, Float> ->
                                        Box(
                                            modifier = Modifier
                                                .height(48.dp)
                                                .weight(pair.second)
                                                .border(1.dp, Color(0xffEDEDED))
                                                .padding(horizontal = 4.dp)
                                                .align(Alignment.CenterVertically),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = pair.first,
                                                style = Theme.typography.footerAddOption.copy(
                                                    fontWeight = FontWeight.Black,
                                                    textAlign = TextAlign.Center
                                                ),
                                            )
                                        }
                                    }
                                }
                            }

                        }
                        items(state.suppliers.filter {
                            it.name.contains(state.searchValue) || it.phone.contains(
                                state.searchValue
                            )
                        }.size) { index ->
                            val currentCustomer = state.suppliers.filter {
                                it.name.contains(state.searchValue) || it.phone.contains(state.searchValue)
                            }[index]
                            val swipeState = rememberSwipeToDismissBoxState()

                            SwipeToDismissBox(rememberSwipeToDismissBoxState(), backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(Color.Red),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row {
                                        IconButton(onClick = {
                                            scope.launch {
                                                swipeState.reset()
                                            }
                                        }) {
                                            Icon(Icons.Default.Refresh, "", tint = Color.White)
                                        }
                                        IconButton(onClick = {
                                            if (swipeState.targetValue == SwipeToDismissBoxValue.StartToEnd)
                                                viewModel.onClickDelete(currentCustomer)
                                        }) {
                                            Icon(Icons.Default.Delete, "", tint = Color.White)
                                        }
                                    }
                                }
                            }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .clickable {
                                            viewModel.onClickItem(currentCustomer)
                                        }
                                        .background(Color.White),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    listOf(
                                        "${index + 1}" to 1f,
                                        currentCustomer.name to 2f,
                                        currentCustomer.phone to 3f,
                                        currentCustomer.costs to 2f,
                                    ).forEach { pair: Pair<String, Float> ->
                                        Box(
                                            modifier = Modifier
                                                .height(48.dp)
                                                .weight(pair.second)
                                                .border(0.25.dp, Color(0xffEDEDED))
                                                .padding(horizontal = 4.dp)
                                                .align(Alignment.CenterVertically),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = pair.first,
                                                style = Theme.typography.headerPhone.copy(
                                                    textAlign = TextAlign.Center
                                                ),
                                                modifier = Modifier,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    AddButton(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = "اضافة مورد",
                        icon = R.drawable.ic_add_client
                    ) {
                        viewModel.navigateToAddNew()
                    }
                }
            }
        }
    }
}



