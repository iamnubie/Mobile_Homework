package com.example.mobile_az

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {
    private val _coins = MutableStateFlow<List<Coin>>(emptyList())
    val coins: StateFlow<List<Coin>> = _coins

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        startFetching()
    }

    private fun startFetching() {
        viewModelScope.launch {
            while (true) {
                try {
                    _isLoading.value = true
                    val result = RetrofitInstance1.api.getCoins()
                    _coins.value = result
                } catch (e: Exception) {
                    Log.e("CryptoViewModel", "Lỗi khi gọi CoinGecko: ${e.localizedMessage}")
                } finally {
                    _isLoading.value = false
                }
                delay(6000) // chờ 6 giây trước khi gọi lại
            }
        }
    }
}

