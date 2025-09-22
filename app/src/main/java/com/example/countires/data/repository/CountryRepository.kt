package com.example.countires.data.repository


import com.example.countires.data.api.ApiService
import com.example.countires.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCountries(): Flow<Result<List<Country>>> = flow {
        try {
            val response = apiService.getAllCountries()
            if (response.isSuccessful) {
                emit(Result.success(response.body() ?: emptyList()))
            } else {
                emit(Result.failure(Exception("Failed to fetch countries: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}