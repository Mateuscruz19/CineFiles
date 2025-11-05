package com.br.cinefiles.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,

        tonalElevation = 4.dp //isso deu o bg, nn sei pq
    ) {
        //SEARCH
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
            selected = false,
            onClick = {
                navController.navigate("search")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            selected = false,
            onClick = { /* TODO: Navegar para Perfil */ }
        )
    }
}