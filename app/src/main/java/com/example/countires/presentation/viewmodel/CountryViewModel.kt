package com.example.countires.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countires.data.model.Country
import com.example.countires.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CountryUiState(
    val countries: List<Country> = emptyList(),
    val filteredCountries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedRegion: String = "All",
    val regions: List<String> = listOf("All")
)

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryUiState())
    val uiState: StateFlow<CountryUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getCountries().collect { result ->
                result.fold(
                    onSuccess = { countries ->
                        val regions =
                            listOf("All") + countries.map { it.region }.distinct().sorted()
                        _uiState.value = _uiState.value.copy(
                            countries = countries,
                            filteredCountries = countries,
                            regions = regions,
                            isLoading = false
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        filterCountries()
    }

    fun updateSelectedRegion(region: String) {
        _uiState.value = _uiState.value.copy(selectedRegion = region)
        filterCountries()
    }

    private fun filterCountries() {
        val currentState = _uiState.value
        val filtered = currentState.countries.filter { country ->
            val matchesSearch = if (currentState.searchQuery.isBlank()) {
                true
            } else {
                country.name.common.contains(currentState.searchQuery, ignoreCase = true)
            }

            val matchesRegion = if (currentState.selectedRegion == "All") {
                true
            } else {
                country.region == currentState.selectedRegion
            }

            matchesSearch && matchesRegion
        }

        _uiState.value = currentState.copy(filteredCountries = filtered)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
