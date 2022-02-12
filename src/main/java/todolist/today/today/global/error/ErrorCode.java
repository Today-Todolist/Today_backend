package todolist.today.today.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "S001", "Internal Server Error"),
    IMPOSSIBLE_TO_GET_IP(500, "S002", "Impossible To Get Ip"),
    CREATE_RANDOM_IMAGE_FAILED(500, "S003", "Create Random Image Failed"),
    FILE_DELETE_FAILED(500, "S004", "Local File Delete Failed"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),
    TOO_MANY_REQUEST(429, "A002", "Too Many Request"),

    NOT_IN_JSON_FORMAT(415, "C001", "Not In Json Format"),
    MISSING_REQUEST(400, "C002", "Missing Request"),
    WRONG_JSON_FORMAT(415, "C003", "Wrong Json Format"),
    WRONG_HTTP_METHOD(405, "C004", "Wrong Http Method"),
    FILE_SAVE_FAILED(400,"C005",  "File Save Failed"),
    WRONG_IMAGE_EXTENSION(400, "C006", "Wrong Image Extension"),
    WRONG_IMAGE_CONTENT_TYPE(400, "C007", "Wrong Image Content Type"),
    MAIL_SEND_FAILED(400, "C008", "Mail Send Failed");

    private final int status;
    private final String code;
    private final String message;

}
