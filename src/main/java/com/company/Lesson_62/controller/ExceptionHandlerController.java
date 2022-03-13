package com.company.Lesson_62.controller;

import com.company.Lesson_62.exeptions.BadRequestException;
import com.company.Lesson_62.exeptions.ForbiddenException;
import com.company.Lesson_62.exeptions.ItemNotFoundException;
import com.company.Lesson_62.exeptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({ItemNotFoundException.class, BadRequestException.class})
    public ResponseEntity<?> handleException(RuntimeException ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handleException(ForbiddenException ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<?> handleException(UnauthorizedException ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
