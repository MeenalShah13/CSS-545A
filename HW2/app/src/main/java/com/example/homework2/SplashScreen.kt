//package com.example.homework2
//
//import android.content.Context
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.safeDrawingPadding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.delay
//
//@Composable
//fun SplashScreen(onSplashScreenFinished: @Composable () -> Unit, context: Context, modifier: Modifier = Modifier) {
//    var helloText = "Hello" + GetUserNameFromSettings(context) + "!"
//    LaunchedEffect(true) {
//        delay(2000)
//        onSplashScreenFinished()
//    }
//
//    Column(modifier = modifier.fillMaxSize()
//        .statusBarsPadding()
//        .safeDrawingPadding(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = helloText,
//            style = MaterialTheme.typography.displayLarge)
//        Spacer(modifier = modifier.height(15.dp))
//        Text(text = "We are getting things ready for you...",
//            style = MaterialTheme.typography.displayMedium)
//    }
//}