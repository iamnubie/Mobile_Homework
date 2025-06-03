package com.example.mobile_az.library_management

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_az.Book
import com.example.mobile_az.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

class StudentListViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    init {
        db.collection("students").addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                _students.value = it.documents.mapNotNull { doc -> doc.toObject(Student::class.java) }
            }
        }
    }

    fun addStudent(name: String) {
        val student = Student(name = name)
        student.id?.let {
            db.collection("students").document(it).set(student)
        }
    }

    fun deleteStudent(id: String) {
        db.collection("students").document(id).delete()
    }
}

class BookListViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    var toastMessage by mutableStateOf<String?>(null)
        private set

    init {
        db.collection("books").addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                _books.value = it.documents.mapNotNull { doc -> doc.toObject(Book::class.java) }
            }
        }
    }

    fun addBook(title: String) {
        if (title.isBlank()) {
            toastMessage = "Vui lòng nhập tên sách"
            return
        }
        val book = Book(title = title)
        book.id?.let {
            db.collection("books").document(it).set(book)
                .addOnSuccessListener { Log.d("Firestore", "Book added with ID: $it") }
                .addOnFailureListener { e -> Log.e("Firestore", "Error adding book", e) }
        }
    }

    fun deleteBook(id: String) {
        db.collection("books").document(id).delete()
    }

    fun clearToast() {
        toastMessage = null
    }
}
