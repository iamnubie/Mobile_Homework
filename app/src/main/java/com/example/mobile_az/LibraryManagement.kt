package com.example.mobile_az

import android.util.Log
import android.widget.Toast
import androidx.annotation.Keep
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_az.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import java.util.UUID
import androidx.lifecycle.viewmodel.compose.viewModel


// File: MainScreen.kt (navigation & bottom bar)
@Composable
fun MainBottomBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "manage",
            onClick = { navController.navigate("manage") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Quản lý") },
            label = { Text("Quản lý") }
        )
        NavigationBarItem(
            selected = currentRoute == "books",
            onClick = { navController.navigate("books") },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "DS Sách") },
            label = { Text("DS Sách") }
        )
        NavigationBarItem(
            selected = currentRoute == "students",
            onClick = { navController.navigate("students") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Sinh viên") },
            label = { Text("Sinh viên") }
        )
    }
}


// Shared data models
@Keep
data class Book(
    val id: String? = UUID.randomUUID().toString(),
    val title: String? = null
)

@Keep
data class Student(
    val id: String? = UUID.randomUUID().toString(),
    val name: String? = null,
    val borrowedBooks: List<String>? = null
)

@Composable
fun <T> SelectionOverlay(
    items: List<T>,
    getItemLabel: (T) -> String,
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit,
    title: String = "Chọn mục"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 300.dp)
                        .fillMaxWidth()
                ) {
                    items(items) { item ->
                        Text(
                            text = getItemLabel(item),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(item)
                                    onDismiss()
                                }
                                .padding(vertical = 12.dp)
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmReturnDialog(
    book: Book,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Xác nhận trả sách") },
        text = {
            Text("Bạn có chắc muốn trả sách \"${book.title ?: "(Không tên)"}\" không?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Đồng ý")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Huỷ")
            }
        }
    )
}

// ManageScreen: chọn sinh viên và mượn sách
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

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Sinh viên", fontWeight = FontWeight.Bold)
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
            Text("Danh sách sách", fontWeight = FontWeight.Bold)
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
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = true,
                                    onCheckedChange = { if (!it) bookToRemove = book },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF8B004B))
                                )
                                Text(
                                    text = book.title ?: "(Không tên)",
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(12.dp))
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

//    if (showBookPicker) {
//        val borrowedBookIds = students.flatMap { it.borrowedBooks.orEmpty() }.toSet()
//        val availableBooks = books.filter { it.id != null && it.id !in borrowedBookIds }
//
//        SelectionOverlay(
//            items = availableBooks,
//            getItemLabel = { it.title ?: "(Không tên)" },
//            onSelect = { book ->
//                val id = book.id ?: return@SelectionOverlay
//                viewModel.addBookToStudent(selectedStudentId, id) {}
//            },
//            onDismiss = { showBookPicker = false },
//            title = "Chọn sách"
//        )
//    }
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


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
