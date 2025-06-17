package com.example.mobile_az

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController

@Composable
fun PermissionRequestScreen(navController: NavHostController) {
    val context = LocalContext.current

    val showLocationCard = remember { mutableStateOf(false) }
    val showNotificationCard = remember { mutableStateOf(false) }
    val showCameraCard = remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                Toast.makeText(context, "Bạn phải cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                Toast.makeText(context, "Bạn phải cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (!granted) {
                Toast.makeText(context, "Bạn phải cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            IconButton(onClick = { showLocationCard.value = true }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location", modifier = Modifier.size(48.dp))
            }
            IconButton(onClick = { showNotificationCard.value = true }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notification", modifier = Modifier.size(48.dp))
            }
            IconButton(onClick = { showCameraCard.value = true }) {
                Icon(Icons.Default.Face, contentDescription = "Camera", modifier = Modifier.size(48.dp))
            }
        }

        if (showLocationCard.value) {
            PermissionCard(
                icon = Icons.Default.LocationOn,
                title = "Location",
                description = "Allow maps to access your location while you use the app?",
                onAllow = {
                    locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    showLocationCard.value = false
                },
                visibleState = showLocationCard
            )
        }

        if (showNotificationCard.value) {
            PermissionCard(
                icon = Icons.Default.Notifications,
                title = "Notification",
                description = "Please enable notifications to receive updates and reminders",
                onAllow = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                            (context as android.app.Activity), android.Manifest.permission.POST_NOTIFICATIONS
                        )
                        if (showRationale) {
                            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            )
                        }
                    } else {
                        Toast.makeText(context, "Hệ thống đã tự cấp quyền thông báo", Toast.LENGTH_SHORT).show()
                    }
                    showNotificationCard.value = false
                },
                visibleState = showNotificationCard
            )
        }

        if (showCameraCard.value) {
            PermissionCard(
                icon = Icons.Default.Face,
                title = "Camera",
                description = "We need access to your camera to scan QR codes",
                onAllow = {
                    cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    showCameraCard.value = false
                },
                visibleState = showCameraCard
            )
        }
    }
}

@Composable
fun PermissionCard(
    icon: ImageVector,
    title: String,
    description: String,
    onAllow: () -> Unit,
    visibleState: MutableState<Boolean>
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFF5722), modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onAllow,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                ) {
                    Text("Allow")
                }
                OutlinedButton(
                    onClick = {
                        visibleState.value = false
                        Toast.makeText(context, "Bạn phải cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Skip for now")
                }
            }
        }
    }
}
