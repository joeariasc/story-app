package co.wawand.data.repository.story

import co.wawand.config.GENERIC_ERROR
import co.wawand.config.SUCCESS
import co.wawand.data.service.story.StoryService
import co.wawand.routes.story.CommentParams
import co.wawand.routes.story.LikeParams
import co.wawand.routes.story.StoryParams
import co.wawand.utils.BaseResponse

class StoryRepositoryImpl(
    private val storyService: StoryService
) : StoryRepository {
    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getMyStories(userId, page, limit), message = SUCCESS)
    }

    override suspend fun getAllStories(page: Int, limit: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getAllStories(page, limit), message = SUCCESS)
    }

    override suspend fun add(storyParams: StoryParams): BaseResponse<Any> {
        val story = storyService.add(storyParams)
        return if (story != null) {
            BaseResponse.SuccessResponse(data = story, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = GENERIC_ERROR)
        }
    }

    override suspend fun update(id: Int, storyParams: StoryParams): BaseResponse<Any> {
        if (storyService.update(id, storyParams)) {
            return BaseResponse.SuccessResponse(data = null, message = SUCCESS)
        }
        return BaseResponse.ErrorResponse(message = GENERIC_ERROR)
    }

    override suspend fun delete(storyId: Int): BaseResponse<Any> {
        if (storyService.delete(storyId)) {
            return BaseResponse.SuccessResponse(data = null, message = SUCCESS)
        }
        return BaseResponse.ErrorResponse(message = GENERIC_ERROR)
    }

    override suspend fun like(likeParams: LikeParams): BaseResponse<Any> {
        if (storyService.like(likeParams)) {
            return BaseResponse.SuccessResponse(data = null, message = SUCCESS)
        }
        return BaseResponse.ErrorResponse(message = GENERIC_ERROR)
    }

    override suspend fun getLikedStories(userId: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getLikes(userId), message = SUCCESS)
    }

    override suspend fun comment(commentParams: CommentParams): BaseResponse<Any> {
        if (storyService.comment(commentParams)) {
            return BaseResponse.SuccessResponse(data = null, message = SUCCESS)
        }
        return BaseResponse.ErrorResponse(message = GENERIC_ERROR)
    }

    override suspend fun getComments(storyId: Int): BaseResponse<Any> {
        return BaseResponse.SuccessResponse(data = storyService.getComments(storyId), message = SUCCESS)
    }
}