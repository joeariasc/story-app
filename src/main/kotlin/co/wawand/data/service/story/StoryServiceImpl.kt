package co.wawand.data.service.story

import co.wawand.data.db.DatabaseFactory
import co.wawand.data.db.extensions.toCommentWithStories
import co.wawand.data.db.extensions.toLikeJoinedWithStoryAndUser
import co.wawand.data.db.extensions.toStory
import co.wawand.data.db.extensions.toStoryJoinedWithUser
import co.wawand.data.db.schemas.CommentTable
import co.wawand.data.db.schemas.LikeTable
import co.wawand.data.db.schemas.StoryTable
import co.wawand.data.db.schemas.UserTable
import co.wawand.data.models.Comment
import co.wawand.data.models.Like
import co.wawand.data.models.Story
import co.wawand.data.models.common.PaginatedResult
import co.wawand.routes.story.CommentParams
import co.wawand.routes.story.LikeParams
import co.wawand.routes.story.StoryParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class StoryServiceImpl : StoryService {
    override suspend fun get(id: Int): Story? {
        val storyRow = DatabaseFactory.dbQuery { StoryTable.select { StoryTable.id eq id }.first() }
        return storyRow.toStory()
    }

    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story> {
        var pageCount: Long = 0
        var nextPage: Long? = null

        val stories = DatabaseFactory.dbQuery {
            StoryTable
                .innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .select { StoryTable.userId eq userId }
                .orderBy(StoryTable.createdAt, SortOrder.DESC).also {
                    pageCount = it.count() / limit
                    if (page < pageCount)
                        nextPage = page + 1L
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story> {
        var pageCount: Long = 0
        var nextPage: Long? = null

        val stories = DatabaseFactory.dbQuery {
            StoryTable
                .innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .selectAll()
                .orderBy(StoryTable.createdAt, SortOrder.DESC)
                .also {
                    pageCount = it.count() / limit
                    if (page < pageCount)
                        nextPage = page + 1L
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getLikes(storyId: Int): List<Like> {
        return DatabaseFactory.dbQuery {
            LikeTable.innerJoin(StoryTable.innerJoin(UserTable, { UserTable.id }, { StoryTable.userId }),
                { LikeTable.storyId }, { StoryTable.id }
            )
                .select { LikeTable.storyId eq storyId }
                .mapNotNull { it.toLikeJoinedWithStoryAndUser() }
        }
    }

    override suspend fun add(storyParams: StoryParams): Story? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = StoryTable.insert {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return statement?.resultedValues?.get(0).toStory()
    }

    override suspend fun update(id: Int, storyParams: StoryParams): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.update({ StoryTable.id eq id }) {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return result == 1
    }

    override suspend fun delete(storyId: Int): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.deleteWhere { StoryTable.id eq storyId }
        }
        return result == 1
    }

    override suspend fun like(likeParams: LikeParams): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = LikeTable.insert {
                it[this.userId] = likeParams.userId
                it[this.storyId] = likeParams.storyId
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun comment(commentParams: CommentParams): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CommentTable.insert {
                it[this.userId] = commentParams.userId
                it[this.storyId] = commentParams.storyId
                it[this.comment] = commentParams.comment
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun getComments(storyId: Int): List<Comment> {
        return DatabaseFactory.dbQuery {
            CommentTable
                .innerJoin(
                    StoryTable, { StoryTable.id }, { CommentTable.storyId })
                .innerJoin(UserTable, { UserTable.id }, { CommentTable.userId })
                .select { CommentTable.storyId eq storyId }.mapNotNull {
                    it.toCommentWithStories()
                }
        }
    }
}