package co.wawand.data.service.user

import co.wawand.data.db.DatabaseFactory.dbQuery
import co.wawand.data.db.extensions.toUser
import co.wawand.data.db.schemas.UserTable
import co.wawand.data.models.User
import org.jetbrains.exposed.sql.select

class UserServiceImpl : UserService {
    override suspend fun getUser(id: Int): User {
        val userRow = dbQuery { UserTable.select { UserTable.id eq id }.first() }
        return userRow.toUser()!!
    }
}