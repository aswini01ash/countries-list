package com.example.countires.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryName(
    val common: String,
    val official: String
)