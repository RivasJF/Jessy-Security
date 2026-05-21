package dev.rivasjf.digitalbrain.Common.Dto;

import lombok.Builder;

@Builder
public record ApiResponse<T> (
    Boolean success,
    T data,
    String message,
    ApiErrorResponse error
){

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .error(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Success");
    }

    public static ApiResponse<Void> error(String message, Object details) {
        return ApiResponse.<Void>builder()
                .success(false)
                .data(null)
                .message(message)
                .error(ApiErrorResponse.builder()
                        .details(details)
                        .build())
                .build();
    }

    public static ApiResponse<Void> error(String message) {
        return error(message, null);
    }
}
