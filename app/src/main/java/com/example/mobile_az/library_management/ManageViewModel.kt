package com.example.mobile_az.library_management

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_az.Book
import com.example.mobile_az.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

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
