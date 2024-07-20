package com.mobile.api.model

import com.mobile.api.consts.DBTableName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import proto.mobile.api.model.user.user
import proto.mobile.api.model.user.User as UserProtobuf

@Entity
@Table(name = DBTableName.USER_TABLE, schema = "public")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0L,

    @Column(name = "username", unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "firstname")
    var firstname: String? = null,

    @Column(name = "lastname")
    var lastname: String? = null,
) {

    companion object {

        fun toProtobuf(user: User): UserProtobuf =
            user {
                this.username = user.username
                this.password = user.password
            }

        fun fromProtobuf(userProtobuf: UserProtobuf): User {
            return User(
                username = userProtobuf.username,
                password = userProtobuf.password,
            )
        }
    }
}