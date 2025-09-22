package com.example.countires.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val name: String,
    val symbol: String? = null
)