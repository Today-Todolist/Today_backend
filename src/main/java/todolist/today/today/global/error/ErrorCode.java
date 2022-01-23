package todolist.today.today.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "S001", "Internal Server Error"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),
    TOO_MANY_REQUEST(429, "A002", "Too Many Request"),

    NOT_IN_JSON_FORMAT(415, "C001", "Not In Json Format"),
    MISSING_REQUEST(400, "C002", "Missing Request"),
    WRONG_JSON_FORMAT(415, "C003", "Wrong Json Format"),
    WRONG_HTTP_METHOD(405, "C004", "Wrong Http Method");

    private final int status;
    private final String code;
    private final String message;

}
