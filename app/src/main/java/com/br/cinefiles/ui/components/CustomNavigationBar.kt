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

@Composable
fun CustomNavigationBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,

        tonalElevation = 4.dp //isso deu o bg, nn sei pq
    ) {
        //SEARCH
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
            selected = false,
            onClick = { /* TODO: Navegar para Busca */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
            selected = false,
            onClick = { /* TODO: Navegar para Menu */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            selected = false,
            onClick = { /* TODO: Navegar para Perfil */ }
        )
    }
}