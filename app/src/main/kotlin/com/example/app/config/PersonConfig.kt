package com.example.app.config

import com.example.app.model.Person
import com.example.app.repository.PersonRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate


// Populating the database with some example data
@Configuration
class PersonConfig (private val personRepository: PersonRepository){
    @Bean
    fun init(): CommandLineRunner {
        return CommandLineRunner {
            val personOne = Person(1, "Jeff", "Smith", "js@test.com", 1603812461, LocalDate.of(2000, 1, 1), "jsmith", "secure_password123")
            val personTwo = Person(2, "Alice", "Johnson", "aj@test.com", 1234567890, LocalDate.of(1995, 5, 10), "ajohnson", "password456")
            val personThree = Person(3, "Bob", "Brown", "bb@test.com", 9876543210, LocalDate.of(1987, 8, 20), "bbrown", "pass1234")
            val personFour = Person(4, "John", "Davis", "ed@test.com", 5555555555, LocalDate.of(1992, 12, 15), "edavis", "mysecurepass")
            val personFive = Person(5, "Johnny", "Wilson", "mw@test.com", 9999999999, LocalDate.of(1980, 3, 5), "mwilson", "p@\$\$w0rd")
            val personSix = Person(6, "Emily", "Jones", "ej@test.com", 5444454999, LocalDate.of(1981, 5, 5), "ejones", "p@\$\$w0rd123")
            val personSeven = Person(7, "David", "Wilson", "dw@test.com", 8888888888, LocalDate.of(1984, 7, 11), "dwilson", "pass7890")
            val personEight = Person(8, "Sarah", "Anderson", "sa@test.com", 7777777777, LocalDate.of(1990, 2, 25), "sanderson", "mysecret123")
            val personNine = Person(9, "Michael", "Brown", "mb@test.com", 6666666666, LocalDate.of(1982, 9, 30), "mbrown", "mypassword456")
            val personTen = Person(10, "Laura", "Taylor", "lt@test.com", 5555555555, LocalDate.of(1991, 6, 17), "ltaylor", "password789")
            val personEleven = Person(11, "James", "Johnson", "jj@test.com", 4444444444, LocalDate.of(1988, 4, 21), "jjohnson", "securepass123")
            val personTwelve = Person(12, "Emma", "Smith", "es@test.com", 3333333333, LocalDate.of(1994, 10, 8), "esmith", "pass9876")
            personRepository.saveAll(listOf(personOne, personTwo, personThree, personFour, personFive, personSix, personSeven, personEight, personNine, personTen, personEleven, personTwelve))
        }
    }
}