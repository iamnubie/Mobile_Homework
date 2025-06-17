package com.example.mobile_az.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_az.RetrofitInstance
import com.example.mobile_az.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _apiTasks = MutableStateFlow<List<Task>>(emptyList())
    private val _temporaryTasks = MutableStateFlow<List<Task>>(emptyList())

    val tasks: StateFlow<List<Task>> = combine(_apiTasks, _temporaryTasks) { api, temp ->
        api + temp
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    private val _visibleCount = MutableStateFlow(5)
    val visibleCount: StateFlow<Int> = _visibleCount

    init {
        loadTasks()
    }

    fun increaseVisibleCount() {
        _visibleCount.value += 1
    }

    fun loadTasks() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllTasks()
                _apiTasks.value = response.data ?: emptyList()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to load tasks", e)
            }
        }
    }

    fun loadTaskDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTaskById(id)
                _selectedTask.value = response.data
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to load task detail", e)
            }
        }
    }

    fun deleteTask(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteTask(id)
                _selectedTask.value = null
                _apiTasks.value = _apiTasks.value.filterNot { it.id == id } // Cập nhật lại không cần loadTasks()
                onSuccess()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to delete task", e)
            }
        }
    }

    fun addTemporaryTask(title: String, description: String) {
        val currentAllTasks = _apiTasks.value + _temporaryTasks.value
        val newId = (currentAllTasks.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        val newTask = Task(
            id = newId,
            title = title,
            description = description,
            status = "Pending",
            priority = "Medium",
            category = "Personal",
            dueDate = null,
            createdAt = null,
            updatedAt = null,
            subtasks = emptyList(),
            attachments = emptyList(),
            reminders = emptyList()
        )
        _temporaryTasks.value = _temporaryTasks.value + newTask
    }
}
