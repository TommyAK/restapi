package com.example.app.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.Period

@Entity
@Table
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long,
    private var name: String,
    private var surname: String,
    private var email: String,
    private var phone: Long,
    private var dateOfBirth: LocalDate,
    private var username: String,
    private var password: String
) {
    private var age: Int = calculateAge(dateOfBirth)


    // Getter methods
    fun getId(): Long {
        return id
    }
    fun getName(): String {
        return name
    }
    fun getSurname(): String {
        return surname
    }
    fun getEmail(): String {
        return email
    }
    fun getPhone(): Long {
        return phone
    }
    fun getDateOfBirth(): LocalDate {
        return dateOfBirth
    }
    fun getAge(): Int {
        return age
    }
    fun getUsername(): String {
        return username
    }
    fun getPassword(): String {
        return password
    }

    // Setter methods
    fun setId(id: Long) {
        this.id = id
    }
    fun setAge(age: Int) {
        this.age = age
    }
    fun setName(name: String) {
        this.name = name
    }
    fun setSurname(surname: String) {
        this.surname = surname
    }
    fun setEmail(email: String) {
        this.email = email
    }
    fun setPhone(phone: Long) {
        this.phone = phone
    }
    fun setDateOfBirth(dateOfBirth: LocalDate) {
        this.dateOfBirth = dateOfBirth
    }
    fun setUsername(username: String) {
        this.username = username
    }
    fun setPassword(password: String) {
        this.password = password
    }

    // Calculate a person's age based off their date of birth
    fun calculateAge(dateOfBirth: LocalDate): Int {
        val currentDate = LocalDate.now()
        val age = Period.between(dateOfBirth, currentDate)
        return age.years
    }

    override fun toString(): String {
        return "Person:\n id = $id, \n firstname = $name, \n surname = $surname,\n email = $email,\n phone = $phone,\n dob = $dateOfBirth,\n age = ${getAge()}"
    }

}
/*
fun main() {
    // Testing the person entity
    val personOne = Person(1,"Jeff", "Smith", "j@test.com", 12345, LocalDate.of(2000, 1, 1), "jeff1", "secure")
    println(personOne)
}
*/

