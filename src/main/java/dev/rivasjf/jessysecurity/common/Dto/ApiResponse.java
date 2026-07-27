package dev.rivasjf.jessysecurity.common.Dto;

import lombok.Builder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Builder
public class ApiResponse {
    public static <T> ResponseEntity<T> success(HttpStatusCode status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }

    public static <T> ResponseEntity<T> success(HttpStatusCode status, T body, String header, String headerValue) {
        return ResponseEntity
                .status(status)
                .header(header,headerValue)
                .body(body);
    }

    public static ResponseEntity<ApiErrorResponse> error(HttpStatusCode status, String message, Object details) {
        var response = ApiErrorResponse.builder()
                .message(message)
                .details(details)
                .build();
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
