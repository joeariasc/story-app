package co.wawand.routes.story

import co.wawand.data.repository.story.StoryRepository
import co.wawand.routes.DEFAULT_LIMIT_SIZE
import co.wawand.routes.DEFAULT_PAGE_START
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.storyRoutes(repository: StoryRepository) {
    routing {
        authenticate {
            route("story") {

                get("my/{page}") {
                    val page = call.parameters["page"]?.toIntOrNull() ?: DEFAULT_PAGE_START
                    val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: DEFAULT_LIMIT_SIZE
                    val result = repository.getAllStories(page, limit)
                    call.respond(result.statusCode, result)
                }

                get("all/{page}") {
                    val page = call.parameters["page"]?.toIntOrNull() ?: DEFAULT_PAGE_START
                    val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: DEFAULT_LIMIT_SIZE
                    val result = repository.getAllStories(page, limit)
                    call.respond(result.statusCode, result)
                }

                post("add") {
                    val params = call.receive<StoryParams>()
                    val result = repository.add(params)
                    call.respond(result.statusCode, result)
                }

                put("update/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: -1
                    val params = call.receive<StoryParams>()
                    val result = repository.update(id, params)
                    call.respond(result.statusCode, result)
                }

                delete("delete/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: -1
                    val result = repository.delete(id)
                    call.respond(result.statusCode, result)
                }

                // comment
                post("add_comment") {
                    val params = call.receive<CommentParams>()
                    val result = repository.comment(params)
                    call.respond(result.statusCode, result)
                }

                get("{story_id}/comments") {
                    val storyId = call.parameters["story_id"]?.toIntOrNull() ?: -1
                    val result = repository.getComments(storyId)
                    call.respond(result.statusCode, result)
                }

                // like
                post("add_like") {
                    val params = call.receive<LikeParams>()
                    val result = repository.like(params)
                    call.respond(result.statusCode, result)
                }

                get("{story_id}/likes") {
                    val userId = call.parameters["story_id"]?.toIntOrNull() ?: -1
                    val result = repository.getLikedStories(userId)
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}