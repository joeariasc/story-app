package co.wawand.data.db

import co.wawand.data.db.schemas.CommentTable
import co.wawand.data.db.schemas.LikeTable
import co.wawand.data.db.schemas.StoryTable
import co.wawand.data.db.schemas.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(StoryTable)
            SchemaUtils.create(LikeTable)
            SchemaUtils.create(CommentTable)
        }
    }

    //Database connection
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql:storyapp?user=jarias"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }

    //DDL(Data manipulation language) Generic function
    suspend fun <T> dbQuery(query: () -> T): T = withContext(Dispatchers.IO) {
        transaction {
            query()
        }
    }
}