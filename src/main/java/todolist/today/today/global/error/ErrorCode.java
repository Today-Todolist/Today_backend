package todolist.today.today.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "S001", "Internal Server Error"),

    NOT_IN_JSON_FORMAT(415, "C001", "Not In Json Format"),
    MISSING_REQUEST(400, "C002", "Missing Request"),
    WRONG_JSON_FORMAT(415, "C003", "Wrong Json Format");

    private final int status;
    private final String code;
    private final String message;

}
