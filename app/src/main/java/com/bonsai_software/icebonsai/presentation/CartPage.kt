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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.bonsai_software.icebonsai.R
import com.bonsai_software.icebonsai.presentation.models.Dessert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(dessertsViewModel: DessertsViewModel, navigationController: NavHostController) {
    val total by dessertsViewModel.total.observeAsState(0)
    val showDialog by dessertsViewModel.showDialog.observeAsState(false)
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Por cobrar") }, navigationIcon = {
            IconButton(onClick = { navigationController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
    }) { innerPadding ->
        if (total == 0) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.cart_empty_message),
                    textAlign = TextAlign.Center
                )
                OutlinedButton(onClick = { navigationController.popBackStack() }) {
                    Text(text = stringResource(id = R.string.go_home))
                }
            }
        } else {
            BodyCart(innerPadding, dessertsViewModel)
            if (showDialog) ConfirmFinishBuyDialog(dessertsViewModel = dessertsViewModel)
        }
    }
}

@Composable
fun ConfirmFinishBuyDialog(dessertsViewModel: DessertsViewModel) {
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
fun BodyCart(innerPadding: PaddingValues, dessertsViewModel: DessertsViewModel) {
    val dessertsCart by dessertsViewModel.dessertCart.observeAsState()
    LazyColumn(Modifier.padding(innerPadding)) {
        dessertsCart?.map {
            val quantity = it.value.size
            val dessert = it.value.last()
            item {
                DessertCartDetailItem(Modifier.padding(8.dp),
                    quantity,
                    dessert,
                    { dessertsViewModel.onDessertAdded(dessert) },
                    { dessertsViewModel.onDessertRemoved(dessert) })
            }
        }
        item {
            Subtotal(dessertsViewModel.total.value)
        }
        item {
            Box(Modifier.padding(16.dp)) {
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp),
                    onClick = { dessertsViewModel.onConfirmDialog() }) {
                    Text(text = stringResource(id = R.string.finish_buy))
                }
            }
        }
    }
}

@Composable
fun Subtotal(total: Int?) {
    Box(Modifier.padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(id = R.string.subtotal))
            Text(text = "$$total", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DessertCartDetailItem(
    modifier: Modifier,
    quantity: Int,
    dessert: Dessert,
    onDessertAdded: (Dessert) -> Unit,
    onDessertRemoved: (Dessert) -> Unit
) {
    ElevatedCard(
        modifier = modifier, shape = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = dessert.dessertName, fontWeight = FontWeight.Bold)
                Text(text = "$${dessert.price}")
            }
            RangeSpinner(quantity,
                dessert,
                { onDessertAdded(dessert) },
                { onDessertRemoved(dessert) })
        }
    }
}

@Composable
fun RangeSpinner(
    quantity: Int,
    dessert: Dessert,
    onDessertAdded: (Dessert) -> Unit,
    onDessertRemoved: (Dessert) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FilledTonalIconButton(onClick = { onDessertRemoved(dessert) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Remove")
        }
        Text(text = quantity.toString())
        FilledTonalIconButton(onClick = { onDessertAdded(dessert) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Add")
        }
    }
}
