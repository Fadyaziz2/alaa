package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.DropDownMenuItem
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FormTextField
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.PositiveButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddAccountClientScreen(viewModel: AddAccountClientViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    AccountsContent(
        state,
        updateName = viewModel::updateName,
        updatePhoneNumber = viewModel::updatePhoneNumber,
        updateCategory = viewModel::updateCategory,
        addClient = viewModel::addClient
    )
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AddAccountClientEffect.ShowToastError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is AddAccountClientEffect.SucceedAddClient -> {
                    Toast.makeText(context, "تم اضافة العميل بنجاح", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }

                else -> TODO()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun AccountsContent(
    state: AddAccountClientState,
    updateName: (String) -> Unit = {},
    updatePhoneNumber: (String) -> Unit = {},
    updateCategory: (Int, String) -> Unit,
    addClient: () -> Unit = {},
) {
    val navController = LocalNavigationProvider.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(Color.Black)
                .height(60.dp)
                .fillMaxWidth()
                .padding(horizontal = 23.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                navController.popBackStack()
                            }
                        })
                Text(
                    "اضافة عميل جديد",
                    style = Theme.typography.headerMainTitle,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Box(Modifier.size(14.dp))
            }
        }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            FormTextField(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                text = state.name,
                isNumber = false,
                onValueChange = {
                    updateName(it)
                },
                hint = "اسم العميل"
            )
            Spacer(Modifier.height(20.dp))
            FormTextField(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                text = state.phoneNumber,
                onValueChange = {
                    updatePhoneNumber(it)
                },
                hint = "رقم الهاتف",
                trailing = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone_numer),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            )
            Spacer(Modifier.height(20.dp))
            if (state.isLoading) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 4.dp,
                        color = Theme.colors.orange
                    )
                }
            }
            else {
                DropDownMenuItem(
                    hint = "اختر التصنيف",
                    list = state.categories.map {
                        it.name
                    },
                    selectedPos = state.selectedCategoryPosition,
                    text = state.selectedCategoryName,
                    onItemSelected = { index, title ->
                        updateCategory(index,title)
                    }
                )
            }
            Spacer(Modifier.weight(1f))
            PositiveButton(
                text = "اضافة العميل",
                isLoading = state.isLoadingAddClient,
                isEnabled = state.visibilityAddClientButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                addClient()
            }
            Spacer(Modifier.weight(1f))
        }
    }
}