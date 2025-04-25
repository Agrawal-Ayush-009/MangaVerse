package com.flatify.mangaverse.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatify.mangaverse.presentation.navigation.MainApp
import com.flatify.mangaverse.presentation.navigation.MainNavGraph
import com.flatify.mangaverse.presentation.ui.FaceRecognition.PermissionResultRequest
import com.flatify.mangaverse.presentation.ui.MangaScreen.MangaViewModel
import com.flatify.mangaverse.presentation.ui.SignupScreen.AuthViewModel
import com.flatify.mangaverse.presentation.ui.theme.MangaVerseTheme
import com.flatify.mangaverse.utils.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var cameraPermissionRequest: PermissionResultRequest
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            cameraPermissionRequest.onPermissionResult(isGranted)
        }

        cameraPermissionRequest = PermissionResultRequest(permissionLauncher)

        context = applicationContext

        setContent {
//            MainNavGraph(
//                context = context,
//                cameraPermissionRequest = cameraPermissionRequest
//            )
            MainApp(
                cameraPermissionRequest = cameraPermissionRequest,
                context = context
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MangaVerseTheme {
        Greeting("Android")
    }
}