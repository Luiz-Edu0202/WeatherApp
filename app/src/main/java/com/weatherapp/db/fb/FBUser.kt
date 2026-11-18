package com.weatherapp.db.fb

import com.weatherapp.model.User

class FBUser {
    var name: String? = null
    var email: String? = null

    fun toUser(): User {
        return User(
            name = name ?: "Usuário",
            email = email ?: ""
        )
    }
}

fun User.toFBUser(): FBUser {
    val fbUser = FBUser()
    fbUser.name = this.name
    fbUser.email = this.email
    return fbUser
}