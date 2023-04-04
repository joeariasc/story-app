package co.wawand.config

import co.wawand.utils.BaseResponse
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<MismatchedInputException> { call, cause ->
            val error = when (cause) {
                is MissingKotlinParameterException -> BaseResponse.ErrorResponse("Missing attribute `${cause.parameter.name}`")
                else -> BaseResponse.ErrorResponse(cause.message!!)
            }
            call.respond(error.statusCode, error)
        }
        exception<JsonParseException> { call, cause ->
            call.respond(BaseResponse.ErrorResponse(cause.message!!))
        }
    }
}