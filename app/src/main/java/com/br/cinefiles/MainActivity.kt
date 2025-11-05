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
import com.br.cinefiles.ui.theme.CinefilesTheme
import com.br.cinefiles.ui.views.Login
import com.br.cinefiles.ui.views.HomeScreen
import com.br.cinefiles.ui.views.LoginScreen
import com.br.cinefiles.ui.views.MovieDetailScreen
import com.br.cinefiles.ui.views.RegisterScreen
import com.br.cinefiles.ui.views.SearchScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinefilesTheme(darkTheme = true) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    composable("welcome") {
                        Login(
                            onCadastroClick = { navController.navigate("register") },
                            onEntrarClick = { navController.navigate("login") }
                        )
                    }

                    composable("login") {
                        LoginScreen(
                            onLoginClick = { navController.navigate("home") },
                            onRegisterClick = { navController.navigate("register") }
                        )
                    }

                    composable("register") {
                        RegisterScreen(
                            onRegisterClick = { navController.navigate("home") },
                            onLoginClick = { navController.navigate("login") }
                        )
                    }

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
}
