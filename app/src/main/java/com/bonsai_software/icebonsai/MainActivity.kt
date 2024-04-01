package com.bonsai_software.icebonsai

import IceBonsaiTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bonsai_software.icebonsai.presentation.CartPage
import com.bonsai_software.icebonsai.presentation.DessertsViewModel
import com.bonsai_software.icebonsai.presentation.HomePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val dessertsViewModel: DessertsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IceBonsaiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = "home") {
                        composable("home") {
                            HomePage(dessertsViewModel, navigationController)
                        }
                        composable("cart") {
                            CartPage(dessertsViewModel, navigationController)
                        }
                    }
                }
            }
        }
    }
}
