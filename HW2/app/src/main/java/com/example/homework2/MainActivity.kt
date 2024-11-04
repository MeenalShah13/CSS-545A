package com.example.homework2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework2.ui.theme.Homework2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Homework2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PossiblePictureEditor(
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun PossiblePictureEditor(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "UserSettings") {
        composable("UserSettings") {
            UserSettings(navController = navController, context = context, modifier = modifier)
        }
//        composable("SplashScreen") {
//            SplashScreen(onSplashScreenFinished = { navigateToMainScreen(navController, context, modifier) }, context = context, modifier = Modifier)
//        }
        composable("MainScreen") {
            MainScreen(navController = navController, context = context, modifier = modifier)
        }
    }
}


@Composable
fun navigateToMainScreen(navController: NavController, context: Context, modifier: Modifier) {
    MainScreen(navController = navController, context = context, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Homework2Theme {
//        UserSettings()
    }
}