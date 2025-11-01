package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.home.Home
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.CustomOutlinedTextField
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.FABButton
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.HeaderText
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.NavigationDrawerCategories
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.TableContentItem
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details.navigateToEditAccountClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountsScreen(viewModel: AccountsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    AccountsContent(
        state = state,
        onClickAllItem = viewModel::onClickAllItem,
        onClickNewCategory = viewModel::addCategory,
        deleteUser = viewModel::deleteAccountClient,
        onClickCategory = viewModel::onClickCategory,
        editCategory = { id, newName -> viewModel.editCategory(categoryId = id, newName = newName) },
        deleteCategory = viewModel::deleteCategory,
        onClickPdf = {id->
            val pdfUrl = "https://alaa-galoo.com/all/accounts/export/"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(pdfUrl + id), "application/pdf")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "لا يوجد تطبيق لعرض PDF", Toast.LENGTH_SHORT).show()
            }
        }
    )
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AccountsEffect.ShowToastError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is AccountsEffect.SucceedAddCategory -> {
                    Toast.makeText(context, "تم اضافة التصنيف بنجاح", Toast.LENGTH_SHORT).show()
                    delay(1000)
                    viewModel.resetAddCategoryStatus()
                }

                is AccountsEffect.SucceedDeleteClient -> {
                    Toast.makeText(context, "تم حذف العميل بنجاح", Toast.LENGTH_SHORT).show()
                }
                is AccountsEffect.SucceedDeleteCategory -> {
                    Toast.makeText(context, "تم حذف التصنيف بنجاح", Toast.LENGTH_SHORT).show()
                }
                is AccountsEffect.SucceedUpdateCategory -> {
                    Toast.makeText(context, "تم تعديل التصنيف بنجاح", Toast.LENGTH_SHORT).show()
                }

                else -> TODO()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun AccountsContent(
    state: AccountsState,
    onClickAllItem: () -> Unit = {},
    onClickNewCategory: (String) -> Unit = {},
    onClickCategory: (Int) -> Unit = {},
    deleteCategory: (Int) -> Unit = {},
    editCategory: (Int, String) -> Unit,
    deleteUser: (Int) -> Unit = {},
    onClickPdf: (Int) -> Unit
) {
    val navController = LocalNavigationProvider.current
    val drawer = rememberDrawerState(initialValue = DrawerValue.Closed)
    var menuState by remember { mutableStateOf(false) }
    var clickedUser by remember { mutableStateOf<User?>(null) }
    var showUserDialog by remember { mutableStateOf(false) }
    var showDeleteUserDialog by remember { mutableStateOf(false) }
    var showLongPressDialog by remember { mutableStateOf(false) }
    var showDeleteCategoryDialog by remember { mutableStateOf(false) }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    var longPressedCategory by remember { mutableStateOf<CategoryUi?>(null) }
    var editableTextFieldCategory by remember { mutableStateOf("") }
    LaunchedEffect(menuState) {
        if (menuState) drawer.open()
        else drawer.close()
    }
    BackHandler {
        if (menuState) menuState = false
        else navController.popBackStack()
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalNavigationDrawer(drawerState = drawer, gesturesEnabled = false, drawerContent = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    drawerContainerColor = Color.Transparent,
                    drawerShape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                ) {
                    NavigationDrawerCategories(
                        isLoading = state.isLoading,
                        isLoadingAddCategory = state.isLoadingAddCategory,
                        isSucceedAddCategory = state.isSucceedAddCategory,
                        list = state.categoriesUi,
                        onClickDismiss = {
                            menuState = false
                        },
                        onClickItem = { index ->
                            onClickCategory(index)
                            menuState = false
                        },
                        onPressLongItem = { category ->
                            longPressedCategory = category
                            menuState = false
                            showLongPressDialog = true
                        },
                        onClickAllItem = {
                            menuState = false
                            onClickAllItem()
                        },
                        isSelectedAll = state.isSelectedAll,
                        numOfAllUsers = state.allUsers.size,
                        onClickNewCategory = { text ->
                            onClickNewCategory(text)
                        })
                }
            }
        }) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Scaffold { _ ->
                    Box(Modifier.fillMaxSize()) {
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
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        "حساباتي",
                                        style = Theme.typography.headerMainTitle,
                                        color = Color.White
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        state.selectedCategoryName,
                                        style = Theme.typography.headerMainTitle,
                                        color = Color.White
                                    )
                                    Spacer(Modifier.width(30.dp))
                                    IconButton(onClick = {
                                        onClickPdf(Home.id)
                                    }) {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_pdf_document),
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                    }
                                    Icon(painter = painterResource(id = R.drawable.ic_menu),
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .pointerInput(Unit) {
                                                detectTapGestures {
                                                    menuState = true
                                                }
                                            })
                                }
                            }
                            AnimatedContent(state.isLoading, label = "") {
                                if (it) {
                                    Box(
                                        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                                    ) {
                                        LoadingIndicator()
                                    }
                                } else {
                                    Column(Modifier) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .background(Theme.colors.yellow)
                                                .padding(horizontal = 20.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "اجمالي المستحقات: ${state.totalShowingReceivables}",
                                                style = Theme.typography.tableHeader.copy(
                                                    fontSize = 13.sp, lineHeight = 24.36.sp
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                "اجمالي الديون: ${state.totalShowingDept}",
                                                style = Theme.typography.tableHeader.copy(
                                                    fontSize = 13.sp, lineHeight = 24.36.sp
                                                ),
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.End
                                            )
                                        }
                                        Spacer(Modifier.height(10.dp))
                                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                            item {
                                                Row(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .height(48.dp)
                                                        .background(Theme.colors.greyTableHeader)
                                                        .padding(horizontal = 20.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "اسم العميل"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "رقم التليفون"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(1f),
                                                        text = "عدد العمليات"
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    HeaderText(
                                                        modifier = Modifier.weight(0.5f),
                                                        text = "المبلغ"
                                                    )
                                                }
                                            }
                                            items(state.users) { user ->
                                                TableContentItem(user) {
                                                    clickedUser = user
                                                    showUserDialog = true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        FABButton(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = "اضافة عميل",
                            icon = R.drawable.ic_add_client
                        ) {
                            navController.navigateToAddAccountClient()
                        }
                        if (showUserDialog) {
                            Dialog(
                                onDismissRequest = { showUserDialog = false }
                            ) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        showUserDialog = false
                                                        clickedUser?.let { u ->
                                                            navController.navigateToUserDetails(
                                                                u.id,
                                                                u.mobile,
                                                                u.name
                                                            )
                                                        }
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Menu,
                                                tint = Color.Blue,
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                "عرض الفواتير",
                                                color = Color.DarkGray,
                                                style = Theme.typography.body
                                            )
                                        }
                                        Spacer(Modifier.width(6.dp))
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        showUserDialog = false
                                                        clickedUser?.let { u ->
                                                            navController.navigateToEditAccountClient(
                                                                clientId = u.id,
                                                                phone = u.mobile,
                                                                name = u.name,
                                                                categoryName = u.categoryName,
                                                                categoryId = u.id
                                                            )
                                                        }
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Edit,
                                                tint = Color.Blue,
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                "تعديل العميل",
                                                color = Color.DarkGray,
                                                style = Theme.typography.body
                                            )
                                        }
                                        Spacer(Modifier.width(6.dp))
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        showUserDialog = false
                                                        showDeleteUserDialog = true
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                tint = Color.Red,
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                "حذف العميل",
                                                color = Color.DarkGray,
                                                style = Theme.typography.body
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (showDeleteUserDialog) {
                            Dialog(onDismissRequest = { showDeleteUserDialog = false }) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 16.dp
                                        ),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "هل انت متاكد من حذف ${clickedUser?.name ?: "العميل"}",
                                            color = Color.DarkGray,
                                            style = Theme.typography.body
                                        )
                                        Spacer(Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    if (!state.isLoadingDeleteClient) {
                                                        showDeleteUserDialog = false
                                                        clickedUser?.let { deleteUser(it.id) }
                                                    }
                                                }
                                            ) {
                                                if (state.isLoadingDeleteClient)
                                                    CircularProgressIndicator(
                                                        color = Color.Red,
                                                        strokeWidth = 2.dp,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                else
                                                    Text(
                                                        "نعم",
                                                        color = Color.Red,
                                                        style = Theme.typography.body,
                                                    )
                                            }
                                            TextButton(
                                                onClick = {
                                                    showDeleteUserDialog = false
                                                }
                                            ) {
                                                Text(
                                                    "لا",
                                                    color = Color.Blue,
                                                    style = Theme.typography.body,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (showLongPressDialog) {
                            Dialog(
                                onDismissRequest = { showLongPressDialog = false }
                            ) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        showLongPressDialog = false
                                                        showEditCategoryDialog = true
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Edit,
                                                tint = Color.Blue,
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                "تعديل التصنيف",
                                                color = Color.DarkGray,
                                                style = Theme.typography.body
                                            )
                                        }
                                        Spacer(Modifier.width(6.dp))
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .pointerInput(Unit) {
                                                    detectTapGestures {
                                                        showLongPressDialog = false
                                                        showDeleteCategoryDialog = true
                                                    }
                                                },
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                tint = Color.Red,
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                "حذف التصنيف",
                                                color = Color.DarkGray,
                                                style = Theme.typography.body
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (showDeleteCategoryDialog) {
                            Dialog(onDismissRequest = { showDeleteCategoryDialog = false }) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 16.dp
                                        ),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "هل انت متاكد من حذف تصنيف ${longPressedCategory?.name ?: "التصنيف"}",
                                            color = Color.DarkGray,
                                            style = Theme.typography.body
                                        )
                                        Spacer(Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    if (!state.isLoadingDeleteClient) {
                                                        showDeleteCategoryDialog = false
                                                        longPressedCategory?.let { deleteCategory(it.id) }
                                                    }
                                                }
                                            ) {
                                                if (state.isLoadingDeleteClient)
                                                    CircularProgressIndicator(
                                                        color = Color.Red,
                                                        strokeWidth = 2.dp,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                else
                                                    Text(
                                                        "نعم",
                                                        color = Color.Red,
                                                        style = Theme.typography.body,
                                                    )
                                            }
                                            TextButton(
                                                onClick = {
                                                    showDeleteCategoryDialog = false
                                                }
                                            ) {
                                                Text(
                                                    "لا",
                                                    color = Color.Blue,
                                                    style = Theme.typography.body,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (showEditCategoryDialog) {
                            Dialog(onDismissRequest = { showEditCategoryDialog = false }) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 16.dp
                                        ),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "تعديل التصنيف",
                                            color = Color.DarkGray,
                                            style = Theme.typography.body
                                        )
                                        Spacer(Modifier.height(32.dp))
                                        CustomOutlinedTextField(
                                            value = editableTextFieldCategory,
                                            onValueChange = { editableTextFieldCategory = it },
                                            label = "ادخل اسم التصنيف الجديد",
                                            cornerRadius = 100.dp
                                        )
                                        Spacer(Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    if (!state.isLoadingUpdateCategory && editableTextFieldCategory != (longPressedCategory?.name
                                                            ?: "")
                                                    ) {
                                                        showEditCategoryDialog = false
                                                        longPressedCategory?.let {
                                                            editCategory(
                                                                it.id,
                                                                editableTextFieldCategory
                                                            )
                                                        }
                                                    }
                                                }
                                            ) {
                                                if (state.isLoadingUpdateCategory)
                                                    CircularProgressIndicator(
                                                        color = Color.Red,
                                                        strokeWidth = 2.dp,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                else
                                                    Text(
                                                        "تعديل",
                                                        color = Color.Blue,
                                                        style = Theme.typography.body,
                                                    )
                                            }
                                            TextButton(
                                                onClick = {
                                                    showEditCategoryDialog = false
                                                }
                                            ) {
                                                Text(
                                                    "لا",
                                                    color = Color.Red,
                                                    style = Theme.typography.body,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}