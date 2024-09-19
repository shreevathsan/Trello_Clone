package com.ps.trelloapp.ExceptionHandler;

import com.ps.trelloapp.domain.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionManager {


    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException nfe) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setOk(false);
        apiResponse.setDate(new Date());
        apiResponse.setMessage(nfe.getMessage());
        ResponseEntity<ApiResponse> response = new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setOk(false);
        apiResponse.setDate(new Date());
        ResponseEntity<ApiResponse> response = new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        return response;

    }
}
