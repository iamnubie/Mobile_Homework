package com.example.mobile_az.library_management

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile_az.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun StudentListScreen() {
    val db = Firebase.firestore
    var students by remember { mutableStateOf(listOf<Student>()) }
    var newName by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        db.collection("students").addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                students = it.documents.mapNotNull { doc -> doc.toObject(Student::class.java) }
            }
        }
    }

    Column(Modifier.padding(16.dp)) {
        TextField(value = newName, onValueChange = { newName = it }, label = { Text("Tên sinh viên") })
        Button(onClick = {
            val student = Student(name = newName)
            student.id?.let { db.collection("students").document(it).set(student) }
            newName = ""
        }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Thêm sinh viên")
        }
        students.forEach { student ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                student.name?.let { Text(it, modifier = Modifier.weight(1f)) }
                IconButton(onClick = { student.id?.let { db.collection("students").document(it).delete() } }) {
                    Icon(Icons.Default.Delete, contentDescription = "Xóa")
                }
            }
        }
    }
}