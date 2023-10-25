package com.example.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig {

    // Two test users with different roles
    @Bean
    fun users(): UserDetailsService {
        val guest = User.builder()
            .username("Guest")
            .password("{bcrypt}\$2y\$10\$1EDCGqa1jH8l2QPsZmD8iewx9cAc/tG12RRNMKCSKs.SruRxE4F1i")
            .roles("GUEST")
            .build()

        val admin = User.builder()
            .username("Admin")
            .password("{bcrypt}\$2y\$10\$SJEldzkXbnJitZfATOa.HOEDsornyKhbz1AobE2uE5SCLwRNS9DDa")
            .roles("GUEST", "ADMIN")
            .build()

        return InMemoryUserDetailsManager(guest, admin)
    }

    // Config for endpoints and user access
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            http {
                formLogin {defaultSuccessUrl("/success", alwaysUse = true) }
                csrf { disable() } // Disabling CSRF for simplicity and testing purposes
            }
            authorizeRequests {
                authorize("/guest/**", hasRole("GUEST"))
                authorize("/admin/**", hasRole("ADMIN"))
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }

    // For development purposes: allows h2-console with spring security enabled
    @Bean
    fun h2ConsoleSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(AntPathRequestMatcher("/h2-console/**"))
        }
    }

}