package com.carla.cinefiles.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.carla.cinefiles.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carla.cinefiles.ui.theme.Branco
import com.carla.cinefiles.ui.theme.Preto
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.carla.cinefiles.ui.theme.Textos

@Composable
fun Login(){
    Column(
       modifier = Modifier
           .fillMaxSize()
           .background(Preto)
           .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(32.dp))

        //LOGO
        Image(
            painter = painterResource(id = R.drawable.cinefileslogo),
            contentDescription = null,
            modifier = Modifier.width(400.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        //IMAGEM PRINCIPAL
        Image(
            painter = painterResource(id = R.drawable.lalaland),
            contentDescription = null,
            modifier = Modifier.width(400.dp).height(450.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        //TITULO 01
        Text(
            text = "Os melhores filmes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            color = Branco
        )
        //TITULO 02
        Text(
            text = "num só lugar.",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Branco
        )

        Spacer(modifier = Modifier.height(20.dp))

        //BOTAO PRINCIPAL
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 40.dp),
        ) {
            Text(
                text = "Cadastre-se",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Branco
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        //BOTAO SECUNDARIO
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Já tem uma conta? ",
                style = MaterialTheme.typography.bodyMedium,
                color = Textos,
            )
            Text(
                text = "Entrar",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,

                modifier = Modifier.clickable {
                    println("mudar aqui pra tela de login")

                }
            )
        }
    }

}

@Preview
@Composable
private fun LoginPreview(){
    Login()
}