package com.example.mobile_az

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.navigation.NavController
import com.example.mobile_az.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val visibleCount by viewModel.visibleCount.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("List", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF2196F3))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("add_new_task")
                    }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add", tint = Color.Red)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = 2,
                onTabSelected = { tab ->
                    if (tab == 0) {
                        navController.navigate("tasklist")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(tasks.take(visibleCount)) { task ->
            val cardColor = when (task.id?.rem(3)) {
                    0 -> Color(0xFFBBDEFB)
                    1 -> Color(0xFFFFCDD2)
                    2 -> Color(0xFFDCEDC8)
                    else -> Color.LightGray
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    backgroundColor = cardColor
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(task.title ?: "", fontWeight = FontWeight.Bold)
                        Text(task.description ?: "")
                    }
                }
            }
        }
    }
}
