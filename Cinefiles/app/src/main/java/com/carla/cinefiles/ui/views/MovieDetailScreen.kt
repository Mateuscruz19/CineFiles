package com.carla.cinefiles.ui.views

import com.carla.cinefiles.ui.components.ImagePlaceholder
import com.carla.cinefiles.ui.components.ActionRow
import com.carla.cinefiles.ui.components.CustomNavigationBar


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DarkThemeColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF212121),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray
)

@Composable
fun MovieDetailScreen() {
    Scaffold(

        //Não sei se vai precisar da topBar, mas ta aq
//        topBar = {
//            TopAppBar(
//                title = { Text("Voltar") },
//                navigationIcon = {
//                    IconButton(onClick = { /* TODO: Ação de voltar */ }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Voltar"
//                        )
//                    }
//                },

//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surface
//                )
//            )
//        },

        // TAB BAR
        bottomBar = {
            CustomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // VSTACK | TITULO + GENERO
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Título",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Gênero",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // IMAGEM PLACEHOLDER
            ImagePlaceholder()

            // DESCRICAO
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin porttitor lacus lobortis orci mattis congue. Duis laoreet lacinia sollicitudin. Pellentesque venenatis erat eu lectus molestie, sit amet posuere nisl auctor. Mauris non auctor erat. Etiam scelerisque sed massa eget volutpat. Cras mollis quis mauris sagittis volutpat. Vestibulum tempor, magna eget porttitor tristique.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Justify
            )

            // VSTACK = DIRECAO + ANO
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Direção: Fulano de Tal",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = "Ano: 2019",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // RATING +     + HEART + ADD
            ActionRow()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}





// --- Preview ---
@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun MovieDetailScreenPreview() {
    MaterialTheme(colorScheme = DarkThemeColors) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailScreen()
        }
    }
}