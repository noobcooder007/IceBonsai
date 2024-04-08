package com.bonsai_software.icebonsai.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.bonsai_software.icebonsai.R
import com.bonsai_software.icebonsai.presentation.models.ShoppingModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingPage(dessertsViewModel: DessertsViewModel, navigationController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Historial") }, navigationIcon = {
            IconButton(onClick = { navigationController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
    }) { innerPadding ->
        BodyShopping(
            innerPadding = innerPadding,
            dessertsViewModel = dessertsViewModel,
            navigationController = navigationController
        )
    }
}

@Composable
fun BodyShopping(
    innerPadding: PaddingValues,
    dessertsViewModel: DessertsViewModel,
    navigationController: NavHostController
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<DessertsUiState>(
        initialValue = DessertsUiState.Loading, key1 = lifecycle, key2 = dessertsViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            dessertsViewModel.uiState.collect { value = it }
        }
    }
    when (uiState) {
        is DessertsUiState.Error -> TODO()
        DessertsUiState.Loading -> {
            LoadingHistory()
        }
        is DessertsUiState.Success -> {
            if ((uiState as DessertsUiState.Success).shopping.isEmpty()) {
                EmptyHistory(navigationController = navigationController)
            } else {
                LazyColumn(Modifier.padding(innerPadding)) {
                    (uiState as DessertsUiState.Success).shopping.map {
                        item {
                            HistoryDetailItem(
                                Modifier.padding(horizontal = 16.dp, vertical = 8.dp), it
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingHistory() {
    LinearProgressIndicator()
}

@Composable
fun EmptyHistory(navigationController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.history_empty_message), textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = { navigationController.popBackStack() }) {
            Text(text = stringResource(id = R.string.go_home))
        }
    }
}

@Composable
fun HistoryDetailItem(
    modifier: Modifier, shopping: ShoppingModel
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
            Text(text = LocalDateTime.parse(shopping.createdAt).format(DateTimeFormatter.ISO_DATE), fontWeight = FontWeight.Bold)
            Text(text = "$${shopping.total}")
        }
    }
}
