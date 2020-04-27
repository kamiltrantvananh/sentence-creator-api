package kamil.demo.kotlin.controller.handlers

import kamil.demo.kotlin.exceptions.ForbiddenWordException
import kamil.demo.kotlin.exceptions.SentenceNotExistException
import kamil.demo.kotlin.exceptions.WordNotExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

/**
 * Custom error handler to avoid stacktrace on output.
 */
@ControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(WordNotExistException::class)
    fun handleWordNotExist(ex: WordNotExistException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDateTime.now(),
                "Word not found.",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(SentenceNotExistException::class)
    fun handleWordNotExist(ex: SentenceNotExistException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDateTime.now(),
                "Sentence not found.",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ForbiddenWordException::class)
    fun handleWordNotExist(ex: ForbiddenWordException, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDateTime.now(),
                "Forbidden word!",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleWordNotExist(ex: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(LocalDateTime.now(),
                "Failed!",
                ex.message!!
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}