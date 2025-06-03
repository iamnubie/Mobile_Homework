package com.example.mobile_az

import android.util.Log
import androidx.annotation.Keep
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
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

class ManageViewModel : ViewModel() {
    private val db = Firebase.firestore

    var students by mutableStateOf(listOf<Student>())
        private set
    var books by mutableStateOf(listOf<Book>())
        private set

    fun loadData() {
        db.collection("students").get().addOnSuccessListener {
            students = it.documents.mapNotNull { doc -> doc.toObject(Student::class.java) }
        }
        db.collection("books").get().addOnSuccessListener {
            books = it.documents.mapNotNull { doc -> doc.toObject(Book::class.java) }
        }
    }

    fun addBookToStudent(studentId: String, bookId: String, onSuccess: () -> Unit) {
        db.collection("students").document(studentId)
            .update("borrowedBooks", FieldValue.arrayUnion(bookId))
            .addOnSuccessListener {
                students = students.map {
                    if (it.id == studentId) it.copy(
                        borrowedBooks = (it.borrowedBooks.orEmpty() + bookId).distinct()
                    ) else it
                }
                onSuccess()
            }
    }

    fun removeBookFromStudent(studentId: String, bookId: String) {
        db.collection("students").document(studentId)
            .update("borrowedBooks", FieldValue.arrayRemove(bookId))
        students = students.map {
            if (it.id == studentId) it.copy(
                borrowedBooks = it.borrowedBooks?.filterNot { it == bookId }
            ) else it
        }
    }
}
