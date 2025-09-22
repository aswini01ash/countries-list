package com.example.countires.data.api

import com.example.countires.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v3.1/all?fields=name,capital,region,flags,population,languages,currencies,timezones")
    suspend fun getAllCountries(): Response<List<Country>>
}
