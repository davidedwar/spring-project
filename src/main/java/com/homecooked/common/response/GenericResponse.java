package com.homecooked.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse<T> {

    private boolean success;
    private String status;
    private T data;

    public static <T> GenericResponse<T> build(Boolean success, String status, T data) {
        return new GenericResponse<>(success, status, data);
    }

    public static <T> GenericResponse<T> success(String message, T data) {
        return new GenericResponse<>(true, message, data);
    }

    public static <T> GenericResponse<T> error(String message, T data) {
        return new GenericResponse<>(false, message, data);
    }

    public ResponseEntity<GenericResponse<T>> toResponseEntity() {
        HttpStatus httpStatus = this.success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(this, httpStatus);
    }
}
