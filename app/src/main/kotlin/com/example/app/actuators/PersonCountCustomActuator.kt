package com.example.app.actuators

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component
import com.example.app.service.PersonService

@Component
@Endpoint(id = "personcount")
// Custom actuator endpoint which will return number of people in the table
class PersonCountCustomActuator(private val personService: PersonService) {

    @ReadOperation
    fun countPersons(): String {
        return personService.personCount()
    }
}