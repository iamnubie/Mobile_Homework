package com.example.mobile_az

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavController, taskId: Int, viewModel: TaskViewModel = viewModel()) {
    LaunchedEffect(taskId) {
        viewModel.loadTaskDetail(taskId)
    }

    val taskState = viewModel.selectedTask.collectAsState()
    val task = taskState.value

    task?.let {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Detail", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2196F3))
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.deleteTask(it.id ?: return@IconButton) {
                                navController.popBackStack()
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF9800))
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(it.title ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(it.description ?: "", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF8D7DA))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryBox("Category", it.category ?: "", Icons.Default.Menu)
                    CategoryBox("Status", it.status ?: "", Icons.Default.Info)
                    CategoryBox("Priority", it.priority ?: "", Icons.Default.Star)
                }


                Spacer(modifier = Modifier.height(20.dp))

                Text("Subtasks", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                it.subtasks?.forEach { sub ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF2F2F2))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = sub.isCompleted == true,
                            onCheckedChange = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(sub.title ?: "", fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("Attachments", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                val context = LocalContext.current
                it.attachments?.forEach { file ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                file.fileUrl?.let { url ->
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                }
                            }
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE0E0E0))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "File",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(file.fileName ?: "No file", fontSize = 14.sp)
                    }
                }
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Đang tải dữ liệu...")
    }
}

@Composable
fun CategoryBox(title: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(icon, contentDescription = title, tint = Color.Black)
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(title, fontSize = 12.sp)
            Text(
                value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}



