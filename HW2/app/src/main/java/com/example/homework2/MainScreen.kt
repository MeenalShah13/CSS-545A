package com.example.homework2

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import coil3.compose.rememberAsyncImagePainter
import com.example.homework2.data.FilterEnum

@Composable
fun MainScreen(navController: NavController, context: Context, modifier: Modifier = Modifier) {
    val onSetUri: (Uri) -> Unit = {}
    val file = context.createImageFile()
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            }
        }
    )
    var tap by remember { mutableStateOf(false) }

    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.packageName}.fileprovider" , file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    Column (modifier = modifier.fillMaxSize()
        .statusBarsPadding()
        .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hello" + GetUserNameFromSettings(context) + "!",
            style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = modifier.height(25.dp))
        Button(onClick = {
            imagePicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
            modifier = modifier.padding(8.dp)) {
            Text(text = stringResource(R.string.select_picture),
                style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                // Request a permission
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        },
            modifier = modifier.padding(8.dp)) {
            Text(text = stringResource(R.string.take_picture),
                style = MaterialTheme.typography.bodyMedium)
        }
        if (capturedImageUri.path?.isNotEmpty() == true) {
            Button(onClick = {
                tap = !tap
            }) {
                LoadImage(capturedImageUri, tap, context, modifier)
            }
            Image(
                modifier = Modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
            )
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

@Composable
fun LoadImage(capturedImageUri: Uri, tap: Boolean, context: Context, modifier: Modifier) {
    val colorMatrix = floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )
    if (tap) {
        if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.GREEN) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.Darken)
            )
        } else if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.RED) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red, blendMode = BlendMode.Darken)
            )
        } else if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.BLUE) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Blue, blendMode = BlendMode.Darken)
            )
        } else if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.BNW) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }
                ))
        } else if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.NEGATIVE) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
        } else if (FilterEnum.valueOf(GetFilterFromSettings(context)) == FilterEnum.BLUR) {
            Image(
                modifier = modifier
                    .padding(16.dp, 8.dp)
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                    ),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null,
            )
        }
    } else {
        Image(
            modifier = modifier
                .padding(16.dp, 8.dp),
            painter = rememberAsyncImagePainter(capturedImageUri),
            contentDescription = null,
            )
    }
}