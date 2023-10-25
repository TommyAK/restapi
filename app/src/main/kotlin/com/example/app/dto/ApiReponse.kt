package com.example.app.dto

data class ApiResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalItems: Long
)
