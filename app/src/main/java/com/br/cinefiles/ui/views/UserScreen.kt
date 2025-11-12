package com.br.cinefiles.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.br.cinefiles.R
import com.br.cinefiles.ui.theme.Branco
import com.br.cinefiles.ui.theme.Preto
import com.br.cinefiles.ui.theme.Textos
import com.br.cinefiles.ui.components.CustomNavigationBar
import androidx.compose.material3.Scaffold

@Composable
fun UserScreen(navController: NavController) {


    Scaffold(
        bottomBar = {

            CustomNavigationBar(navController = navController)
        }
    ) { paddingValues ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Preto)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Perfil",
                tint = Branco,
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Usuário",
                style = MaterialTheme.typography.titleMedium,
                color = Branco,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.titleMedium,
                color = Branco,
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(8.dp))

            FilmesRow()

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Assistidos",
                style = MaterialTheme.typography.titleMedium,
                color = Branco,
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(8.dp))

            FilmesRow()
        }
    }
}

@Composable
fun FilmesRow() {
    val filmes = listOf(
        Pair("Gente Grande", R.drawable.poster_gente_grande),
        Pair("As Branquelas", R.drawable.poster_as_branquelas),
        Pair("Corra", R.drawable.poster_corra)
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filmes.size) { index ->
            val (titulo, imagem) = filmes[index]

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Textos),
                modifier = Modifier
                    .width(140.dp)
                    .height(230.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // IMAGEM com cantos superiores arredondados
                    Image(
                        painter = painterResource(id = imagem),
                        contentDescription = titulo,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp
                                )
                            )
                    )

                    // TEXTO do título com fundo
                    Text(
                        text = titulo,
                        color = Branco,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Textos)
                            .padding(vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun UserScreenPreview() {
//    UserScreen()
//}
