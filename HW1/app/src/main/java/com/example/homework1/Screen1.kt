package com.example.homework1

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Screen1(navController: NavController) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.begin_info_text),
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { navController.navigate("Screen2")}) {
            Image(painter = painterResource(R.drawable.grave),
                contentDescription = stringResource(R.string.grave_desc))
        }
    }
}