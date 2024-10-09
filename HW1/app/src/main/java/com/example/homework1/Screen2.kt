package com.example.homework1

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

@Composable
fun Screen2(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = RandomDateGenerator(),
            color = colorResource(R.color.white)
        )
        Spacer(Modifier.height(20.dp))
        Button(onClick = { navController.navigate("Screen1") }) {
            Text(text = stringResource(R.string.back_text))
        }
    }
}

fun RandomDateGenerator(): String {
    val startCalendar = Calendar.getInstance()
    startCalendar.set(2060, 0, 1)

    val endCalendar = Calendar.getInstance()
    endCalendar.set(2120, 11, 31)

    // Get the time in milliseconds for both dates
    val startMillis = startCalendar.timeInMillis
    val endMillis = endCalendar.timeInMillis

    // Generate a random number between the two times
    val randomMillis = Random.nextLong(startMillis, endMillis)

    // Create a new Date from the random milliseconds
    val randomDate = Date(randomMillis)
    return randomDate.toString()
}