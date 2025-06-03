package com.example.mobile_az.library_management

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_az.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen(viewModel: ManageViewModel = viewModel()) {
    var selectedStudentId by remember { mutableStateOf("") }
    var showStudentPicker by remember { mutableStateOf(false) }
    var showBookPicker by remember { mutableStateOf(false) }
    var bookToRemove by remember { mutableStateOf<Book?>(null) }
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.loadData()
    }

    val students = viewModel.students
    val books = viewModel.books
    val selectedStudent = students.find { it.id == selectedStudentId }
    val borrowedBooks = selectedStudent?.borrowedBooks.orEmpty()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        item {
            Text(
                text = "Hệ thống\nQuản lý thư viện",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            Text("Sinh viên",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                fontSize = 18.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = selectedStudent?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFF8B004B), RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = { showStudentPicker = true }) { Text("Thay đổi") }
            }
        }

        item {
            Text("Danh sách Sách",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 12.dp),
                fontSize = 18.sp
                )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFF4E7E1)
            ) {
                if (borrowedBooks.isEmpty()) {
                    Text(
                        text = "Bạn chưa mượn quyền sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Column(Modifier.padding(12.dp)) {
                        borrowedBooks.mapNotNull { id -> books.find { it.id == id } }.forEach { book ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color.White, RoundedCornerShape(12.dp)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = true,
                                    onCheckedChange = { if (!it) bookToRemove = book },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF8B004B))
                                )
                                Text(
                                    text = book.title ?: "(Không tên)",
                                    modifier = Modifier
//                                        .background(Color.White, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = { showBookPicker = true },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) { Text("Thêm") }
        }
    }

    if (showStudentPicker) {
        SelectionOverlay(
            items = students,
            getItemLabel = { it.name ?: "(Không tên)" },
            onSelect = { selectedStudentId = it.id ?: "" },
            onDismiss = { showStudentPicker = false },
            title = "Chọn sinh viên"
        )
    }

    if (showBookPicker) {
        val borrowedBookIds = students.flatMap { it.borrowedBooks.orEmpty() }.toSet()
        val availableBooks = books.filter { it.id != null && it.id !in borrowedBookIds }

        SelectionOverlay(
            items = availableBooks,
            getItemLabel = { it.title ?: "(Không tên)" },
            onSelect = { book ->
                val id = book.id ?: return@SelectionOverlay
                if (selectedStudentId.isNotBlank()) {
                    viewModel.addBookToStudent(selectedStudentId, id) {}
                } else {
                    Toast.makeText(context, "Vui lòng chọn sinh viên trước khi thêm sách.", Toast.LENGTH_SHORT).show()
                }
            },
            onDismiss = { showBookPicker = false },
            title = "Chọn sách"
        )
    }

    bookToRemove?.let { book ->
        ConfirmReturnDialog(
            book = book,
            onConfirm = {
                book.id?.let { viewModel.removeBookFromStudent(selectedStudentId, it) }
                bookToRemove = null
            },
            onDismiss = { bookToRemove = null }
        )
    }
}


