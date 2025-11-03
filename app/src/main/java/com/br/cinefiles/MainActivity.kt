package com.br.cinefiles

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.br.cinefiles.ui.views.Login
import com.br.cinefiles.ui.views.HomeScreen
import com.br.cinefiles.ui.views.LoginScreen
import com.br.cinefiles.ui.views.MovieDetailScreen
import com.br.cinefiles.ui.views.SearchScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            
            NavHost(
                navController = navController,
                startDestination = "welcome" // A tela inicial agora é a de boas-vindas
            ) {
                // Tela de Boas-Vindas
                composable("welcome") {
                    Login(
                        onCadastroClick = { navController.navigate("home") },
                        onEntrarClick = { navController.navigate("login") } // Navega para a tela de Login real
                    )
                }

                // Tela de Login com campos de E-mail e Senha
                composable("login") {
                    LoginScreen(
                        onCadastroClick = { navController.navigate("home") } // Supondo que daqui também vá para home
                    )
                }

                // Demais telas
                composable("home") {
                    HomeScreen(navController = navController)
                }
                composable("search") {
                    SearchScreen(navController = navController)
                }
                composable(
                    route = "movieDetail/{movieId}",
                    arguments = listOf(navArgument("movieId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getString("movieId")

                    MovieDetailScreen(
                        movieId = movieId,
                        navController = navController
                    )
                }
            }
        }
    }
}
