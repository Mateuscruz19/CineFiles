package com.br.cinefiles.ui.views.OnBoarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.cinefiles.ui.views.OnBoarding.OnBoarding01
import com.br.cinefiles.ui.views.OnBoarding.OnBoarding02
import com.br.cinefiles.ui.views.OnBoarding.OnBoarding03
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OnBoardingScreen(onFinish: () -> Unit) {
    var currentPage by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentPage) {
            0 -> OnBoarding01()
            1 -> OnBoarding02()
            2 -> OnBoarding03()
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentPage > 0) {
                OutlinedButton(onClick = { currentPage-- }) {
                    Text("Voltar")
                }
            } else {
                Spacer(Modifier.width(100.dp))
            }

            Button(onClick = {
                if (currentPage < 2) {
                    currentPage++
                } else {
                    onFinish()
                }
            }) {
                Text(if (currentPage == 2) "Começar" else "Próximo")
            }
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview(){
    OnBoardingScreen(
        onFinish = {}
    )
}