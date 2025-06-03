package com.example.mobile_az.library_management

import android.util.Log
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
import com.example.mobile_az.Book
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import androidx.lifecycle.viewmodel.compose.viewModel

//@Composable
//fun BookListScreen() {
//    val db = Firebase.firestore
//    var books by remember { mutableStateOf(listOf<Book>()) }
//    var newTitle by remember { mutableStateOf("") }
//
//    LaunchedEffect(true) {
//        db.collection("books").addSnapshotListener { snapshot, _ ->
//            snapshot?.let {
//                books = it.documents.mapNotNull { doc -> doc.toObject(Book::class.java) }
//            }
//        }
//    }
//
//    Column(Modifier.padding(16.dp)) {
//        InputField(value = newTitle, onValueChange = { newTitle = it }, label = "Tên sách")
//        Button(onClick = {
//            if (newTitle.isNotBlank()) {
//                val book = Book(title = newTitle)
//                book.id?.let {
//                    db.collection("books").document(it).set(book)
//                        .addOnSuccessListener {
//                            Log.d("Firestore", "Book added with ID: $it")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.e("Firestore", "Error adding book", e)
//                        }
//                }
//                newTitle = ""
//            }
//        }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
//            Text("Thêm sách")
//        }
//
//        books.forEach { book ->
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                book.title?.let { Text(it, modifier = Modifier.weight(1f)) }
//                IconButton(onClick = { book.id?.let { db.collection("books").document(it).delete() } }) {
//                    Icon(Icons.Default.Delete, contentDescription = "Xóa")
//                }
//            }
//        }
//    }
//}

@Composable
fun BookListScreen(viewModel: BookListViewModel = viewModel()) {
    val books by viewModel.books.collectAsState()
    var newTitle by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(viewModel.toastMessage) {
        viewModel.toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Column(Modifier.padding(16.dp)) {
        InputField(value = newTitle, onValueChange = { newTitle = it }, label = "Tên sách")
        Button(
            onClick = {
                viewModel.addBook(newTitle)
                newTitle = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Thêm sách")
        }

        LazyColumn {
            items(books) { book ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                book.title?.let { Text(it, modifier = Modifier.weight(1f)) }
                IconButton(onClick = { book.id?.let(viewModel::deleteBook) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Xóa")
                }
            }
        }
        }
    }
}
