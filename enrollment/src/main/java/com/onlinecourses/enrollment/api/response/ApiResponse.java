package com.onlinecourses.enrollment.api.response;

import java.time.LocalDateTime;
import java.util.List;

public record ApiResponse<T>(
        int status,
        String message,
        T data,
        LocalDateTime timestamp
) {

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return new ApiResponse<>(
                status,
                message,
                data,
                LocalDateTime.now()
        );

    }

    public static ApiResponse<Object> error(int status, String message) {
        return new ApiResponse<>(
                status,
                message,
                List.of(),
                LocalDateTime.now()
        );
    }

}
