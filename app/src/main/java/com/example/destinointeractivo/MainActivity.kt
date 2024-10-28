package com.example.destinointeractivo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.destinointeractivo.navigation.AppNavigation
import com.example.destinointeractivo.ui.theme.DestinoInteractivoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DestinoInteractivoTheme {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                AppNavigation()
            }

        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

    }
}



@Composable
fun InitialScreen() {
    AppNavigation()
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
 */