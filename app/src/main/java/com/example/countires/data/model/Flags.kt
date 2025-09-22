package com.example.countires.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    val png: String,
    val svg: String,
    val alt: String? = null
)