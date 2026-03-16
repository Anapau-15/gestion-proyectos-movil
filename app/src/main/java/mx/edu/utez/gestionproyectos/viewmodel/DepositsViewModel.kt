package mx.edu.utez.gestionproyectos.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.edu.utez.gestionproyectos.data.RetrofitClient
import mx.edu.utez.gestionproyectos.data.SessionManager
import mx.edu.utez.gestionproyectos.model.Deposit

class DepositViewModel : ViewModel() {

    var deposits = mutableStateListOf<Deposit>()

    var isLoading = mutableStateOf(false)

    fun loadDeposits() {

        viewModelScope.launch {

            try {

                isLoading.value = true

                val token = SessionManager.token

                val response =
                    RetrofitClient.apiService.getDeposits("Bearer $token")

                if (!response.error) {

                    deposits.clear()

                    response.data?.let {
                        deposits.addAll(it)
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                isLoading.value = false

            }

        }

    }

}