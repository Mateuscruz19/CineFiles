package com.br.cinefiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.br.cinefiles.ui.views.Login
import com.br.cinefiles.ui.views.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    Login(
                        onCadastroClick = {
                            navController.navigate("home")
                        }
                    )
                }
                composable("home") {
                    HomeScreen()
                }
            }
        }
    }
}

