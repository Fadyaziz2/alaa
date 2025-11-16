package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.cashier

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.base.EventHandler
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.AddButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold

@Composable
fun CashierScreen(viewModel: CashierViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current

    EventHandler(viewModel.effect) { effect ->
        when (effect) {
            is CashierUiEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            CashierUiEffect.NavigateBack -> {
                navController.popBackStack()
            }
        }
    }

    if (state.isFormVisible) {
        CashierForm(
            state = state,
            onNameChange = viewModel::onChangeName,
            onPhoneChange = viewModel::onChangePhone,
            onPasswordChange = viewModel::onChangePassword,
            onSubmit = viewModel::onSubmitForm,
            onBack = viewModel::navigateBackToPrevious
        )
    } else {
        CashierList(
            state = state,
            onAddCashier = viewModel::onClickAddCashier,
            onEdit = viewModel::onEditCashier,
            onDelete = viewModel::onDeleteCashier,
            onBack = viewModel::navigateBackToPrevious
        )
    }
}

@Composable
private fun CashierList(
    state: CashierUiState,
    onAddCashier: () -> Unit,
    onEdit: (CashierItem) -> Unit,
    onDelete: (CashierItem) -> Unit,
    onBack: () -> Unit,
) {
    SalesScaffold(
        title = "الكاشير (${state.cashiers.size})",
        navBack = onBack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
            ) {
                if (state.cashiers.isEmpty()) {
                    CashierEmptyState()
                } else {
                    CashierTable(
                        cashiers = state.cashiers,
                        onEdit = onEdit,
                        onDelete = onDelete
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AddButton(
                    modifier = Modifier,
                    text = "اضافة كاشير",
                    icon = R.drawable.ic_add_client,
                    onClick = onAddCashier
                )
            }
        }
    }
}

@Composable
private fun CashierTable(
    cashiers: List<CashierItem>,
    onEdit: (CashierItem) -> Unit,
    onDelete: (CashierItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F3))
                .padding(vertical = 12.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableHeaderCell(text = "رقم الهاتف", weight = 1.3f)
            TableHeaderCell(text = "اسم الكاشير", weight = 1.1f)
            TableHeaderCell(text = "التشغيل", weight = 0.7f, textAlign = TextAlign.Center)
            TableHeaderCell(text = "الاجراء", weight = 1.0f, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            itemsIndexed(cashiers, key = { _, cashier -> cashier.id }) { index, cashier ->
                CashierRow(
                    index = index,
                    cashier = cashier,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
private fun CashierRow(
    index: Int,
    cashier: CashierItem,
    onEdit: (CashierItem) -> Unit,
    onDelete: (CashierItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(text = cashier.phone, weight = 1.3f)
        TableCell(text = cashier.name, weight = 1.1f)
        TableCell(text = "${index + 1}", weight = 0.7f, textAlign = TextAlign.Center)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onEdit(cashier) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_cashier),
                    contentDescription = "edit cashier",
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { onDelete(cashier) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "delete cashier",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun RowScope.TableHeaderCell(
    text: String,
    weight: Float,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = Theme.typography.headerPhone.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.W600
        ),
        color = Color(0xFF4C4C4C),
        textAlign = textAlign
    )
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = Theme.typography.headerPhone.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.W500
        ),
        color = Color(0xFF191919),
        textAlign = textAlign
    )
}

@Composable
private fun CashierEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cashier_empty_state),
            contentDescription = null,
            modifier = Modifier.height(180.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "لا يوجد كاشير حاليا",
            style = Theme.typography.headerPhone.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600
            ),
            color = Color(0xFF6B6B6B)
        )
    }
}

@Composable
private fun CashierForm(
    state: CashierUiState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
) {
    SalesScaffold(
        title = if (state.isEdit) state.formState.name.ifBlank { "تعديل كاشير" } else "اضافة كاشير",
        navBack = onBack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.formState.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "اسم الكاشير",
                        color = Color(0xFFBFBFBF),
                        style = Theme.typography.headerPhone.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF515151),
                    unfocusedBorderColor = Color(0xFF515151)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = state.formState.phone,
                onValueChange = onPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "رقم الهاتف",
                        color = Color(0xFFBFBFBF),
                        style = Theme.typography.headerPhone.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF515151),
                    unfocusedBorderColor = Color(0xFF515151)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            var passwordVisible by rememberSaveable(state.formState.id) { mutableStateOf(false) }
            OutlinedTextField(
                value = state.formState.password,
                onValueChange = onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "كلمة السر",
                        color = Color(0xFFBFBFBF),
                        style = Theme.typography.headerPhone.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF515151),
                    unfocusedBorderColor = Color(0xFF515151)
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.weight(1f, fill = true))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFF00BA2F)),
                onClick = onSubmit
            ) {
                Text(
                    text = if (state.isEdit) "حفظ التعديل" else "حفظ الكاشير",
                    style = Theme.typography.headerPhone.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600
                    ),
                    color = Color.White
                )
            }
        }
    }
}
