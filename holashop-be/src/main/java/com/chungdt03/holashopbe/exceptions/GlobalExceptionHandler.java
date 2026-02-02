package com.chungdt03.holashopbe.exceptions;

import com.chungdt03.holashopbe.components.TranslateMessages;
import com.chungdt03.holashopbe.exceptions.payload.DataNotFoundException;
import com.chungdt03.holashopbe.exceptions.payload.InvalidParamException;
import com.chungdt03.holashopbe.exceptions.payload.PermissionDenyException;
import com.chungdt03.holashopbe.responses.ApiResponse;
import com.chungdt03.holashopbe.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends TranslateMessages {

    // ✅ 1. Handle custom application exceptions
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("AppException: {}", errorCode, exception);

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(errorCode.getCode()))
                        .error(translate(errorCode.getMessage()))
                        .build());
    }

    // ✅ 2. Handle data not found
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.error("Data not found: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .error(e.getMessage())
                        .build());
    }

    // ✅ 3. Handle invalid parameters
    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<ApiResponse> handleInvalidParamException(InvalidParamException e) {
        log.error("Invalid parameter: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .error(e.getMessage())
                        .build());
    }

    // ✅ 4. Handle data integrity violations (duplicate, constraint violations)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("Data integrity violation: ", e);

        String errorMessage = e.getMessage();

        // Parse specific error messages
        if (errorMessage != null) {
            if (errorMessage.contains("phone_number")) {
                errorMessage = translate(MessageKeys.PHONE_NUMBER_EXISTED);
            } else if (errorMessage.contains("Duplicate entry")) {
                errorMessage = translate(MessageKeys.DUPLICATE_ENTRY);
            } else {
                // Keep the original message if no specific pattern matches
                errorMessage = translate(MessageKeys.ERROR_MESSAGE);
            }
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .error(errorMessage)
                        .build());
    }

    // ✅ 5. Handle authentication failures
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException e) {
        log.error("Bad credentials: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                        .error(e.getMessage())
                        .build());
    }

    // ✅ 6. Handle authorization failures
    @ExceptionHandler({AccessDeniedException.class, PermissionDenyException.class})
    public ResponseEntity<ApiResponse> handleAccessDenied(Exception e) {
        log.error("Access denied: {}", e.getMessage());

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(errorCode.getCode()))
                        .error(translate(errorCode.getMessage()))
                        .build());
    }

    // ✅ 7. Handle transaction rollback exceptions
    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<ApiResponse> handleTransactionRollback(UnexpectedRollbackException e) {
        log.error("Transaction rolled back: ", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .error(translate(MessageKeys.TRANSACTION_ERROR))
                        .build());
    }

    // ✅ 8. Handle all other runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception: ", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .error(translate(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()))
                        .build());
    }

    // ✅ 9. Catch-all for any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception e) {
        log.error("Unexpected exception: ", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .error(translate(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()))
                        .build());
    }
}
