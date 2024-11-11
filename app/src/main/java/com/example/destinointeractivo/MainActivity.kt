package com.example.destinointeractivo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.destinointeractivo.navigation.AppNavigation
import com.example.destinointeractivo.ui.theme.DestinoInteractivoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
       //  Ocultar barras de navegación y notificaciones
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)



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