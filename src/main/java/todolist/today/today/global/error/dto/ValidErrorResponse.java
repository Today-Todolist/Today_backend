package todolist.today.today.global.error.dto;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import todolist.today.today.global.error.ErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidErrorResponse extends BasicErrorResponse {

    private final Map<String, String> reason;

    private ValidErrorResponse(MethodArgumentNotValidException e) {
        super(ErrorCode.MISSING_REQUEST);

        List<FieldError> fieldErrors = e.getFieldErrors();
        this.reason = new HashMap<>(fieldErrors.size());
        e.getFieldErrors().forEach(fieldError -> reason.put(fieldError.getField(), fieldError.getDefaultMessage()));
    }

    public static ValidErrorResponse from(MethodArgumentNotValidException e) {
        return new ValidErrorResponse(e);
    }

}
