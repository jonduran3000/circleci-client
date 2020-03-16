package com.jonduran.circleci.remote

import com.jonduran.circleci.remote.model.User

object TestUserFactory {
    @JvmStatic fun create(): User {
        return User(
            login = "jonduran3000",
            name = "Jon Duran",
            avatarUrl = "https://picsum.photos/id/237/200/200?grayscale"
        )
    }
}