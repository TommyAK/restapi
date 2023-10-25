package com.example.app.repository

import com.example.app.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long>  {

    fun findPersonByEmail(email: String): Person?

    fun findPersonByPhone(phone: Long): Person?

    fun findPersonByUsername(username: String): Person?

    fun findPersonById(id : Long): Person?

    fun countAllBy(): Long

}