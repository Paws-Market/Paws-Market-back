package com.korit.pawsmarket.global.response;

import com.korit.pawsmarket.global.response.enums.Status;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private Status status;
    private String message;
    private T data;

    public ApiResponse(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> generateResp(
            Status status, String message, T data
    ) {
        ApiResponse<T> apiResponse = new ApiResponse<>(status, message, data);
        return ResponseEntity.status(status.getHttpStatus()).body(apiResponse);
    }
}
