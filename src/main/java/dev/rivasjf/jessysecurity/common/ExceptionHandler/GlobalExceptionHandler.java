package dev.rivasjf.jessysecurity.common.ExceptionHandler;

import dev.rivasjf.jessysecurity.common.Dto.ApiErrorResponse;
import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(Exception ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(Exception ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(Exception ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(Exception ex) {
        return ApiResponse.error(HttpStatus.UNAUTHORIZED, "Invalid credentials", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }
}
