package com.example.boaviagemsarah.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.boaviagemsarah.components.MyTopBar
import com.example.boaviagemsarah.dataBase.AppDataBase
import com.example.boaviagemsarah.viewmodels.DadosViewModel
import com.example.boaviagemsarah.viewmodels.DadosViewModelFactory

private fun isSelected(currentDestination: NavDestination?, route:String): Boolean {
    return currentDestination?.hierarchy?.any {it.route == route} == true
}

@Composable
fun Home(ida: String) {
    Scaffold(
        topBar = {
            MyTopBar("App Boa Viagem") {System.exit(1)}
        }
    ) {
        val db = AppDataBase.getDatabase(LocalContext.current)
        val dadosViewModel: DadosViewModel = viewModel(
            factory = DadosViewModelFactory(db)
        )
        LaunchedEffect(ida) {
            if (ida != null) {
                val user = dadosViewModel.findById(ida.toLong())
                user?.let { dadosViewModel.setUiState(it) }
            }
        }
        val state = dadosViewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row {
                Text(
                    text = "Bem Vindo! " + state.value.login,
                    fontSize = 22.sp
                )
            }
        }
    }

}

@Composable
fun Menu(ida: String){

    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.value?.destination

            BottomNavigation {

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "home"),
                    onClick = { navController.navigate("home") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "destino"),
                    onClick = { navController.navigate("destino") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "sobre"),
                    onClick = { navController.navigate("sobre") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = ""
                        )
                    }
                )
            }
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ){
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    Home(ida)
                }
                composable("destino"){
                    Destinos()
                }

                composable("sobre"){
                    Sobre()
                }


            }
        }
    }
}