package com.example.boaviagemsarah.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.boaviagemsarah.dataBase.AppDataBase
import com.example.boaviagemsarah.models.Destino
import com.example.boaviagemsarah.viewmodels.DestinoViewModel
import com.example.boaviagemsarah.viewmodels.DestinoViewModelFactory

fun dest(){

}

@Composable
fun Destinos() {

    val destinoViewModel: DestinoViewModel = viewModel(
        factory = DestinoViewModelFactory(AppDataBase.getDatabase(LocalContext.current))
    )

    val destinosLista = destinoViewModel.getAll().collectAsState(initial = emptyList())

    val navController = rememberNavController()

    val ctx = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    navController.navigate("viagem/${-1L}")

//                    Toast.makeText(
//                        ctx, "novo",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = ""
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            NavHost(
                navController = navController,
                startDestination = "dest"
            ) {
                composable("viagem/{destinoId}", arguments = listOf(navArgument("destinoId"){
                    type = NavType.LongType ; defaultValue = -1L})) { entry ->
                    entry.arguments?.getLong("destinoId").let { it
                        Viagens(
                            onBack = {navController.navigateUp()}, it
                        )
                    }
                }

                composable("dest") {
                    dest()
                }

            }

            LazyColumn {
                items(items = list) {
                    DestinoCard(p = it)
                }
            }


        }
    }

}

@Composable
fun DestinoCard(p: Destino) {
    val ctx = LocalContext.current
    Card(elevation = CardDefaults.cardElevation(
        defaultElevation = 8.dp
    ),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable {
                Toast
                    .makeText(
                        ctx, "Destino: ${p.destino}",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
    ) {
        Column(modifier = Modifier.padding(4.dp)) {

            Row {

                if (p.finalidade == "lazer") {
                    Image(
                        painter = painterResource(id = com.example.boaviagemsarah.R.drawable.lazer),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(55.dp)
                            .weight(0.8f)
                            .padding(top = 8.dp, end = 10.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = com.example.boaviagemsarah.R.drawable.trabalho),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(55.dp)
                            .weight(0.8f)
                            .padding(top = 8.dp, end = 10.dp)
                            .clip(CircleShape)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(3f)
                ) {
                    Row {
                        Text(
                            text = p.destino,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 22.sp
                        )
                    }

                    Row {
                        Text(
                            text = "Inicio ${p.inicio} - Fim ${p.fim}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Row {
                        Text(
                            text = "Orçamento R$${p.valor}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
