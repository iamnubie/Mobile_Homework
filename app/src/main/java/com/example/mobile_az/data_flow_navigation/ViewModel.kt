package com.example.mobile_az.data_flow_navigation

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mobile_az.ForgotPasswordState

class ForgotPasswordViewModel : ViewModel() {
    var state by mutableStateOf(ForgotPasswordState())
}
