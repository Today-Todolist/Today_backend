package todolist.today.today.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todolist.today.today.global.error.dto.BasicErrorResponse;
import todolist.today.today.global.error.dto.JsonErrorResponse;
import todolist.today.today.global.error.dto.ValidErrorResponse;
import todolist.today.today.global.error.exception.BasicException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        final BasicErrorResponse response = BasicErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ValidErrorResponse response = ValidErrorResponse.from(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        final JsonErrorResponse response = JsonErrorResponse.from(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final JsonErrorResponse response = JsonErrorResponse.from(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<?> handleBasicException(BasicException e) {
        final BasicErrorResponse response = BasicErrorResponse.from(e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}
