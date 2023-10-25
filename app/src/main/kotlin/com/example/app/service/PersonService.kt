package com.example.app.service

import com.example.app.model.Person
import org.springframework.stereotype.Service
import com.example.app.repository.PersonRepository
import jakarta.transaction.Transactional
import java.time.LocalDate
import java.util.NoSuchElementException

@Service
class PersonService(private val personRepository: PersonRepository) {

    // Adding a person with checks to ensure username, email, or phone are not already in use
    @Transactional
    fun addPerson(person: Person): Person {
        val existingId = personRepository.findPersonById(person.getId())
        val existingUsername = personRepository.findPersonByUsername(person.getUsername())
        val existingEmail = personRepository.findPersonByEmail(person.getEmail())
        val existingPhone = personRepository.findPersonByPhone(person.getPhone())

        if (existingId != null) {
            throw IllegalStateException("A person with the provided id already exists.")
        }
        if (existingUsername != null) {
            throw IllegalStateException("A person with the provided username already exists.")
        }
        if (existingEmail != null) {
            throw IllegalStateException("A person with the provided email already exists.")
        }
        if (existingPhone != null) {
            throw IllegalStateException("A person with the provided phone number already exists.")
        }

        return personRepository.save(person)
    }

    // Update a user detail's individually or all at once, given a user's id
    @Transactional
    fun updatePerson(id: Long, fieldUpdates: Map<String, Any>): Person {
        val existingPerson = personRepository.findPersonById(id)
            ?: throw NoSuchElementException("Person with ID $id not found.")

        // Allows for all and/or individual fields to be updated
        fieldUpdates.forEach { (fieldName, value) ->
            when (fieldName) {
                "name" -> existingPerson.setName(value as String)
                "surname" -> existingPerson.setSurname(value as String)
                "email" -> {
                    val newEmail = value as String
                    // Checking to ensure email not already in use by another user
                    if (newEmail != existingPerson.getEmail()) {
                        val existingPersonWithNewEmail = personRepository.findPersonByEmail(newEmail)
                        if (existingPersonWithNewEmail != null && existingPersonWithNewEmail != existingPerson) {
                            throw NoSuchElementException("A person with the provided email already exists.")
                        }
                    }
                    existingPerson.setEmail(newEmail)
                }
                "phone" -> {
                    val newPhone = value as Int
                    // Checking to ensure phone number not already in use by another user
                    if (newPhone.toLong() != existingPerson.getPhone()) {
                        val existingPersonWithNewPhone = personRepository.findPersonByPhone(newPhone.toLong())
                        if (existingPersonWithNewPhone != null && existingPersonWithNewPhone != existingPerson) {
                            throw NoSuchElementException("A person with the provided phone number already exists.")
                        }
                    }
                    existingPerson.setPhone(newPhone.toLong())
                }
                "dateOfBirth" -> {
                    if (value is String) {
                        val dateOfBirth = LocalDate.parse(value)
                        existingPerson.setDateOfBirth(dateOfBirth)
                        // Re-calculating age based off new dateOfBirth and updating
                        val newAge = existingPerson.calculateAge(dateOfBirth)
                        existingPerson.setAge(newAge)
                    } else {
                        throw NoSuchElementException("Invalid date format for dateOfBirth field.")
                    }
                }
                "username" -> {
                    val newUsername = value as String
                    // Checking to ensure username not already in use by another user
                    if (newUsername != existingPerson.getUsername()) {
                        val existingPersonWithNewUsername = personRepository.findPersonByUsername(newUsername)
                        if (existingPersonWithNewUsername != null && existingPersonWithNewUsername != existingPerson) {
                            throw NoSuchElementException("A person with the provided username already exists.")
                        }
                    }
                    existingPerson.setUsername(newUsername)
                }
                "password" -> existingPerson.setPassword(value as String)
            }
        }

        return personRepository.save(existingPerson)
    }

    // Delete a user by username
    @Transactional
    fun deletePerson(username: String) {
        val person = personRepository.findPersonByUsername(username)

        if (person != null) {
            personRepository.delete(person)
        } else {
            throw NoSuchElementException("Provided username does not exist.")
        }
    }

    // Return all users stored in the h2 database
    fun getPersons(): List<Person> {
        return personRepository.findAll()
    }

    // Person count for custom actuator
    fun personCount(): String {
        val count = personRepository.countAllBy()
        return "Number of persons in table: $count"
    }

}