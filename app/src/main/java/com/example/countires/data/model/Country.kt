package com.example.countires.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: CountryName,
    val capital: List<String>? = null,
    val region: String,
    val flags: Flags,
    val population: Long,
    val languages: Map<String, String>? = null,
    val currencies: Map<String, Currency>? = null,
    val timezones: List<String>
)