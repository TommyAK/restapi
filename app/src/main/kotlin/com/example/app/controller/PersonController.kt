package com.example.app.controller

import com.example.app.dto.ApiResponse
import com.example.app.dto.PersonDTO
import com.example.app.model.Person
import com.example.app.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class PersonController (@Autowired private val personService: PersonService) {

    // Simple endpoint to provide feedback that the login attempt was successful
    @GetMapping("/success")
    fun loginSuccess(): String {
        return "Login was successful."
    }

    // Returns a list of users that can be filtered by name and/or age (excludes sensitive info)
    @GetMapping("/guest/users")
    fun getUserListGuest(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) age: Int?): List<PersonDTO> {

        val persons = if (!name.isNullOrBlank() && age != null) {
            // Name and age filter
            personService.getPersons().filter { person ->
                person.getName().contains(name, ignoreCase = true) && person.getAge() == age
            }
        } else if (!name.isNullOrBlank()) {
            // Name only filter
            personService.getPersons().filter { person -> person.getName().contains(name, ignoreCase = true)
            }
        } else if (age != null) {
            // Age only filter
            personService.getPersons().filter { person -> person.getAge() == age
            }
        } else {
            // Return everyone if no search filter provided
            personService.getPersons()
        }

        // Map to exclude sensitive information
        return persons.map { person ->
            PersonDTO(
                id = person.getId(),
                name = person.getName(),
                surname = person.getSurname(),
                email = person.getEmail(),
                phone = person.getPhone(),
                dateOfBirth = person.getDateOfBirth(),
                age = person.getAge()
            )
        }
    }

    // Returns a list of users with pagination
    @GetMapping("/admin/users")
    fun getUserListAdmin(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) age: Int?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") pageSize: Int
    ): ApiResponse<Person> {
        // Store all persons
        val allPersons = personService.getPersons()

        // Filters are applied if provided
        val filteredPersons = if (!name.isNullOrBlank() && age != null) {
            // Name and age filter
            allPersons.filter { person ->
                person.getName().contains(name, ignoreCase = true) && person.getAge() == age
            }
        } else if (!name.isNullOrBlank()) {
            // Name only filter
            allPersons.filter { person -> person.getName().contains(name, ignoreCase = true) }
        } else if (age != null) {
            // Age only filter
            allPersons.filter { person -> person.getAge() == age }
        } else {
            // No filters provided
            allPersons
        }

        // Code for pagination (max. 5 items per page)
        val pageRequest = PageRequest.of(page, pageSize)
        val paginatedPersons = filteredPersons.subList(
            pageRequest.pageNumber * pageRequest.pageSize,
            minOf((pageRequest.pageNumber + 1) * pageRequest.pageSize, filteredPersons.size)
        )

        // Returning entries
        return ApiResponse(
            content = paginatedPersons,
            totalPages = (filteredPersons.size + pageSize - 1) / pageSize,
            totalItems = filteredPersons.size.toLong()
        )
    }

    // Add a new person by inputting required details
    @PostMapping("/admin/users/add-person/")
    fun addPerson(@RequestBody person: Person): ResponseEntity<Any> {
        return try {
            personService.addPerson(person)
            ResponseEntity("Person with username ${person.getUsername()} added successfully.", HttpStatus.CREATED)
        } catch (e: IllegalStateException) {
            val errorMessage = e.message ?: "An error occurred while adding the person ${person.getUsername()}."
            val errorResponse = mapOf("error" to errorMessage)
            ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }
    }

    // Remove a person based upon their username
    @DeleteMapping("/admin/users/delete-person/{username}")
    fun deletePerson(@PathVariable username: String): ResponseEntity<Any> {
        return try {
            personService.deletePerson(username)
            ResponseEntity("Person with username $username deleted successfully", HttpStatus.CREATED)
        } catch (e: NoSuchElementException) {
            val errorMessage = e.message ?: "Person with username $username not found."
            val errorResponse = mapOf("error" to errorMessage)
            ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
        }
    }

    // Given a valid id, update a person's details by providing single or multiple fields
    @PutMapping("/admin/users/update-person/{id}")
    fun updatePerson(
        @PathVariable id: Long,
        @RequestBody fieldUpdates: Map<String, Any>
    ): ResponseEntity<Any> {
        return try {
            val updatedPersonEntity = personService.updatePerson(id, fieldUpdates)
            ResponseEntity.ok(updatedPersonEntity)
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().body("Error: ${e.message}")
        }
    }

}

