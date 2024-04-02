package com.bonsai_software.icebonsai.presentation

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.bonsai_software.icebonsai.R
import com.bonsai_software.icebonsai.presentation.models.DessertModel
import com.bonsai_software.icebonsai.presentation.models.DessertsType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(dessertsViewModel: DessertsViewModel, navigationController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Ice Bonsai") }, actions = {
            CartAction(navigationController)
        })
    }) { innerPadding ->
        BodyDesserts(innerPadding, dessertsViewModel)
    }
}

@Composable
fun BodyDesserts(innerPaddingValues: PaddingValues, dessertsViewModel: DessertsViewModel) {
    val total by dessertsViewModel.total.observeAsState(0)
    val showDialog by dessertsViewModel.showDialog.observeAsState(false)
    val desserts = getDessertsMenu().toMutableStateList()
    Box(
        Modifier.padding(innerPaddingValues)
    ) {
        Column(
            Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .fillMaxSize()
        ) {
            TotalSale(total)
            ItemsForSale(desserts, dessertsViewModel)
            PayButton(Modifier.fillMaxWidth(), total) { dessertsViewModel.onConfirmDialog() }
            if (showDialog) ConfirmBuyDialog(dessertsViewModel)
        }
    }
}

@Composable
fun ConfirmBuyDialog(dessertsViewModel: DessertsViewModel) {
    Dialog(onDismissRequest = { dessertsViewModel.onDismissDialog() }) {
        Card {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.dialog_title,
                        dessertsViewModel.total.value!!
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = { dessertsViewModel.onDismissDialog() }) {
                        Text(text = stringResource(id = R.string.dialog_cancel))
                    }
                    TextButton(onClick = { dessertsViewModel.finishBuy() }) {
                        Text(text = stringResource(id = R.string.dialog_accept))
                    }
                }
            }
        }
    }
}

@Composable
fun PayButton(modifier: Modifier, total: Int, onConfirmDialog: () -> Unit) {
    FilledTonalButton(
        enabled = total > 0,
        shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp),
        onClick = { onConfirmDialog() }, modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.finish_buy))
    }
}

fun getDessertsMenu(): List<DessertModel> {
    return listOf(
        DessertModel(
            dessertName = "Cono Sencillo",
            dessertType = DessertsType.SINGLE_ICE_CREAM,
            price = 7
        ),
        DessertModel(
            dessertName = "Vaso Sencillo",
            dessertType = DessertsType.SINGLE_CUP_ICE_CREAM,
            price = 7
        ),
        DessertModel(
            dessertName = "Cono Doble",
            dessertType = DessertsType.DOUBLE_ICE_CREAM,
            price = 13
        ),
        DessertModel(
            dessertName = "Vaso Doble",
            dessertType = DessertsType.DOUBLE_CUP_ICE_CREAM,
            price = 13
        ),
        DessertModel(
            dessertName = "Cazuelita",
            dessertType = DessertsType.GOBLET_ICE_CREAM,
            price = 16
        ),
        DessertModel(
            dessertName = "Sandwich",
            dessertType = DessertsType.SANDWICH_ICE_CREAM,
            price = 10
        ),
        DessertModel(
            dessertName = "Trompo",
            dessertType = DessertsType.VANILLA_ICE_POP,
            price = 10
        ),
        DessertModel(
            dessertName = "Esquimal",
            dessertType = DessertsType.ESKIMO_ICE_POP,
            price = 13
        ),
    )
}

@Composable
fun ItemsForSale(desserts: List<DessertModel>, dessertsViewModel: DessertsViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(desserts) {
            DessertItem(dessert = it) { dessertsViewModel.onDessertAdded(it) }
        }
    }
}

@Composable
fun DessertItem(dessert: DessertModel, onDessertAdded: (DessertModel) -> Unit) {
    ElevatedButton(
        onClick = { onDessertAdded(dessert) },
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(4.dp),
        shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = dessert.dessertName,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(text = "$${dessert.price}", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun TotalSale(total: Int) {
    Card(Modifier.fillMaxWidth()) {
        Box(Modifier.padding(12.dp)) {
            Text(
                text = "$$total",
                fontSize = 60.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CartAction(navigationController: NavHostController) {
    IconButton(onClick = { navigationController.navigate("cart") }) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart, contentDescription = "cart"
        )
    }
}
