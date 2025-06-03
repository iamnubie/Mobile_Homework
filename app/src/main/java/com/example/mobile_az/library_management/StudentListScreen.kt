package com.example.mobile_az.library_management

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile_az.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudentListScreen(viewModel: StudentListViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    var newName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(Modifier.padding(16.dp)) {
        InputField(value = newName, onValueChange = { newName = it }, label = "Tên sinh viên")
        Button(
            onClick = {
                if (newName.isBlank()) {
                    Toast.makeText(context, "Vui lòng nhập tên sinh viên", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addStudent(newName)
                    newName = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Thêm sinh viên")
        }

        LazyColumn {
            items(students) { student ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                student.name?.let { Text(it, modifier = Modifier.weight(1f)) }
                IconButton(onClick = { student.id?.let(viewModel::deleteStudent) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Xóa")
                }
            }
        }
        }
    }
}
