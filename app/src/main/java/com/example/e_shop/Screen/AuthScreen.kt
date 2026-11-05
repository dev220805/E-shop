package com.example.e_shop.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.e_shop.R

@Composable
fun AuthScreen(modifier: Modifier = Modifier, navController: NavController){
    Column (
        modifier = modifier.fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ){
       Image(painter = painterResource(id = R.drawable.banner), contentDescription = "banner",
        modifier = Modifier.fillMaxWidth()
            .height(300.dp)
       )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Start your shopping journey now",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "The Best Platform for Shopping",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {navController.navigate("Login")},
            modifier = Modifier.fillMaxWidth()
                .height(50.dp)
            ) {
            Text(text = "Login", fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(onClick = {navController.navigate("Signup")},
            modifier = Modifier.fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Signup", fontSize = 22.sp)
        }


    }

}