package com.korit.pawsmarket.global.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.korit.pawsmarket.global.exception.CustomException;
import com.korit.pawsmarket.global.exception.InvalidException;
import com.korit.pawsmarket.global.response.ApiResponse;
import com.korit.pawsmarket.global.response.Response;
import com.korit.pawsmarket.global.response.enums.ResponseCode;
import com.korit.pawsmarket.global.response.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<String>> handleAllExceptions(Exception e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Response<String>(ResponseCode.GENERAL_ERROR, e.getMessage()));
    }

    // 커스텀 예외를 처리하는 메서드
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        Status status;

        try {
            status = Status.valueOf(e.getClass().getSimpleName().replace("Exception", "").toUpperCase());
            // 예외 이름에서 Exception을 제외 => 상태 값 추출
        } catch (IllegalArgumentException ex) {
            status = Status.ERROR;
            // 상태가 없으면 기본값을 설정
        }

        log.error("Custom exception : {}", status);
        return ApiResponse.generateResp(status, e.getMessage(), null);
    }

    // 유효성 검사 실패시 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()    // 첫번째 에러만 반환
                .orElse("잘못된 요청입니다.");

        log.error("Validation error : {}", errorMessage);
        return ApiResponse.generateResp(Status.INVALID, errorMessage, null);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidException(InvalidException ex) {
        String errorMessage = ex.getMessage();

        log.error("Invalid data error : {}", errorMessage);

        return ApiResponse.generateResp(Status.INVALID, errorMessage, null);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidFormatException(InvalidFormatException ex) {
        // 잘못 입력된 값 가져오기
        Object invalidValue = ex.getValue();

        // Enum 클래스 가져오기 (CategoryType과 같은 Enum)
        Class<?> targetType = ex.getTargetType();

        // Enum에 정의된 모든 값 가져오기
        String validValues = Arrays.stream(targetType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        // 동적으로 에러 메시지 생성
        String errorMessage = String.format("잘못된 카테고리 타입입니다. 입력값: [%s], 허용된 값: [%s]", invalidValue, validValues);

        log.error("Enum Parsing Error : {}", errorMessage);

        return ApiResponse.generateResp(Status.INVALID, errorMessage, null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause(); // 원인 예외 가져오기

        // 원인이 `InvalidFormatException`이면 별도로 처리
        if (cause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormatException(invalidFormatException);
        }

        // 그 외의 경우 일반 JSON 파싱 에러 메시지 반환
        return ApiResponse.generateResp(Status.ERROR, "잘못된 요청 형식입니다.", null);
    }
}
