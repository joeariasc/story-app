package co.wawand.data.service.user

import co.wawand.data.models.User

interface UserService {
    suspend fun getUser(id: Int): User
}