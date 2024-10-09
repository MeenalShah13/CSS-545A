package com.example.homework1

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework1.ui.theme.Homework1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Homework1Theme {
                Surface (modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.black)
                ) {
                    DeathCalculator(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun DeathCalculator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screen1") {
        composable("Screen1") {
            Screen1(navController = navController)
        }
        composable("Screen2") {
            Screen2(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeathCalculatorPreview() {
    Homework1Theme {
        DeathCalculator()
    }
}