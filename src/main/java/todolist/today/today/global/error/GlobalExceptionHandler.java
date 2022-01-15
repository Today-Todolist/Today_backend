package todolist.today.today.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todolist.today.today.global.error.dto.BasicErrorResponse;
import todolist.today.today.global.error.dto.InvoluteErrorResponse;
import todolist.today.today.global.error.dto.SimpleErrorResponse;
import todolist.today.today.global.error.exception.BasicException;
import todolist.today.today.global.error.exception.GlobalException;
import todolist.today.today.global.error.exception.InvoluteException;
import todolist.today.today.global.error.exception.SimpleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        final BasicErrorResponse response = new BasicErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvoluteErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> reasons = new HashMap<>(fieldErrors.size());
        e.getFieldErrors().forEach(fieldError -> reasons.put(fieldError.getField(), fieldError.getDefaultMessage()));

        final InvoluteErrorResponse response = new InvoluteErrorResponse(ErrorCode.MISSING_REQUEST, reasons);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<SimpleErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String reason = "The content type must be application/json. But request content type is " + e.getContentType();

        final SimpleErrorResponse response = new SimpleErrorResponse(ErrorCode.NOT_IN_JSON_FORMAT, reason);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<SimpleErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final SimpleErrorResponse response = new SimpleErrorResponse(ErrorCode.WRONG_JSON_FORMAT, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler({BasicException.class, InvoluteException.class, SimpleException.class})
    public <T extends GlobalException<R>, R extends BasicErrorResponse> ResponseEntity<BasicErrorResponse> handleGlobalException(T e) {
        R response = e.getErrorResponse();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}
