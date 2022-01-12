package todolist.today.today.global.error.dto;

import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import todolist.today.today.global.error.ErrorCode;

@Getter
public class JsonErrorResponse extends BasicErrorResponse {

    private final String reason;

    private JsonErrorResponse(HttpMediaTypeNotSupportedException e) {
        super(ErrorCode.NOT_IN_JSON_FORMAT);
        this.reason = "The content type must be application/json. But request content type is " + e.getContentType();
    }

    private JsonErrorResponse(HttpMessageNotReadableException e) {
        super(ErrorCode.WRONG_JSON_FORMAT);
        this.reason = e.getMessage();
    }

    public static JsonErrorResponse from(HttpMediaTypeNotSupportedException e) {
        return new JsonErrorResponse(e);
    }

    public static JsonErrorResponse from(HttpMessageNotReadableException e) {
        return new JsonErrorResponse(e);
    }

}
