package com.mobile.api.validate

import com.mobile.api.model.User
import jakarta.validation.ValidationException
import org.springframework.stereotype.Component

@Component
class UserValidate {

    fun validate(user: User) {
        isValidUsername(user.username)
        isValidPassword(user.password)
    }

    private fun isValidUsername(username: String) {
        if (username.isBlank()) {
            throw ValidationException("Username cannot be blank")
        }

        if (USERNAME_INVALID_CHARACTER.containsMatchIn(username)) {
            throw ValidationException("Username cannot contain characters")
        }
    }

    private fun isValidPassword(password: String) {
        if (password.isBlank()) {
            throw ValidationException("Password cannot be blank")
        }
    }

    companion object {
        private val USERNAME_INVALID_CHARACTER = "^[а-яёЁ%@#$^&*()_=-]*$".toRegex()
    }
}