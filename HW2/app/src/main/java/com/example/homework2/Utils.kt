package com.example.homework2

import android.content.Context

fun GetUserNameFromSettings(context: Context): String {
    val userSettingsLines = context.openFileInput("UserSettings").bufferedReader().readLines()
    return userSettingsLines[0]
}

fun GetFilterFromSettings(context: Context): String {
    val userSettingsLines = context.openFileInput("UserSettings").bufferedReader().readLines()
    return userSettingsLines[1]
}