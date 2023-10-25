package com.example.app.dto

import java.time.LocalDate

data class PersonDTO(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val phone: Long,
    val dateOfBirth: LocalDate,
    val age: Int
)