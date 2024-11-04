package com.example.homework2

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homework2.data.FilterEnum
import java.io.FileNotFoundException

@Composable
fun UserSettings(navController: NavController, context: Context, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var itemPostition by remember { mutableStateOf(0) }
    var userName by remember { mutableStateOf("") }
    val checkSettingsFileExists = CheckSettingsFileExists(context)
    val filterList = FilterEnum.values()
    Column (modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.user_settings_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = modifier.height(25.dp))
        if (!checkSettingsFileExists) {
            UserName(modifier,
                onValueChange = {
                    userName = it
                }, userName)
        } else {
            userName = GetUserNameFromSettings(context)
        }
        Text(text = stringResource(R.string.filter_label),
            style = MaterialTheme.typography.bodyMedium)
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                expanded = true
            }) {
            Text(text = filterList[itemPostition].toString(),
                style = MaterialTheme.typography.bodyMedium)
            Icon(Icons.Outlined.ArrowDropDown, contentDescription = "DropDown Icon")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filterList.forEachIndexed{index, filterEnums ->
                DropdownMenuItem(text = {
                    Text(text = filterEnums.toString())
                },
                    onClick = {
                        expanded = false
                        itemPostition = index
                    })
            }
        }
        Spacer(modifier = modifier.height(300.dp))
        Button(onClick = {
            var fileContent = userName + "\n" + filterList[itemPostition].toString()
            SubmitSettings(fileContent = fileContent, context = context)
            navController.navigate("MainScreen")
        }) {
            Text(text = "Submit",
                style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun UserName(modifier: Modifier, onValueChange: (String) -> Unit, userName: String) {
    Row(modifier = modifier.width(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text =  stringResource(R.string.username_label),
            style = MaterialTheme.typography.bodyMedium)
        TextField(value = userName,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = modifier)
    }
}

private fun CheckSettingsFileExists(context: Context): Boolean {
    try {
        val userSettingsLines = context.openFileInput("UserSettings").bufferedReader().readLines()
        return true
    } catch (error: FileNotFoundException) {
        return false
    }
}

private fun SubmitSettings(fileContent: String, context: Context) {
    context.openFileOutput("UserSettings", Context.MODE_PRIVATE).use {
        it.write(fileContent.toByteArray())
    }
}