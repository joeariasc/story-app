package co.wawand.data.db.extensions

import co.wawand.data.db.schemas.CommentTable
import co.wawand.data.db.schemas.LikeTable
import co.wawand.data.db.schemas.StoryTable
import co.wawand.data.db.schemas.UserTable
import co.wawand.data.models.Comment
import co.wawand.data.models.Like
import co.wawand.data.models.Story
import co.wawand.data.models.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow?.toUser(): User? {
    return if (this == null) null
    else User(
        id = this[UserTable.id],
        fullName = this[UserTable.fullName],
        nickName = this[UserTable.nickName],
        email = this[UserTable.email],
        createdAt = this[UserTable.createdAt].toString(),
    )
}

fun ResultRow?.toStory(): Story? {
    return if (this == null) null
    else Story(
        id = this[StoryTable.id],
        title = this[StoryTable.title],
        content = this[StoryTable.content],
        isDraft = this[StoryTable.isDraft],
        createdAt = this[StoryTable.createdAt].toString()
    )
}

fun ResultRow?.toStoryJoinedWithUser(): Story? {
    return if (this == null) null
    else Story(
        id = this[StoryTable.id],
        user = User(
            id = this[UserTable.id],
            fullName = this[UserTable.fullName],
            nickName = this[UserTable.nickName],
            email = this[UserTable.email],
            createdAt = this[UserTable.createdAt].toString()
        ),
        title = this[StoryTable.title],
        content = this[StoryTable.content],
        isDraft = this[StoryTable.isDraft],
        createdAt = this[StoryTable.createdAt].toString()
    )
}

fun ResultRow?.toLikeJoinedWithStoryAndUser(): Like? {
    return if (this == null) null
    else Like(
        id = this[LikeTable.id],
        story = Story(
            id = this[StoryTable.id],
            user = User(
                id = this[UserTable.id],
                fullName = this[UserTable.fullName],
                nickName = this[UserTable.nickName],
                email = this[UserTable.email],
                createdAt = this[UserTable.createdAt].toString()
            ),
            title = this[StoryTable.title],
            content = this[StoryTable.content],
            isDraft = this[StoryTable.isDraft],
            createdAt = this[StoryTable.createdAt].toString()
        ),
        createdAt = this[LikeTable.createdAt].toString()
    )
}

fun ResultRow?.toCommentWithStories(): Comment? {
    return if (this == null) null
    else Comment(
        id = this[CommentTable.id],
        user = User(
            id = this[UserTable.id],
            fullName = this[UserTable.fullName],
            nickName = this[UserTable.nickName],
            email = this[UserTable.email],
            createdAt = this[UserTable.createdAt].toString()
        ),
        story = Story(
            id = this[StoryTable.id],
            user = null,
            title = this[StoryTable.title],
            content = this[StoryTable.content],
            isDraft = this[StoryTable.isDraft],
            createdAt = this[StoryTable.createdAt].toString()
        ),
        comment = this[CommentTable.comment],
        createdAt = this[CommentTable.createdAt].toString()
    )
}