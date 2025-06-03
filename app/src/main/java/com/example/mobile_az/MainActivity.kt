package com.example.mobile_az

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_az.ui.theme.Mobile_AZTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        setContent {
            Mobile_AZTheme {

//                    MyInfo(
//                        data = Data(
//                            image = R.drawable.avatar,
//                            name = "Nguyen Hong Minh",
//                            address = "To Ky, Tan Chanh Hiep"
//                        ),
//                        modifier = Modifier.padding(innerPadding)
//                    )

//                    Outline(modifier = Modifier.padding(innerPadding))

//                    InputInfo(modifier = Modifier.padding(innerPadding))

                    AppNavigation()

            }
        }
    }
}
