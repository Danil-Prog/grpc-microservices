package com.mobile.api.repository

import com.mobile.api.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

}