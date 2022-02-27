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
    UNAUTHORIZED_REQUEST(401, "A003", "Unauthorized Request"),

    NOT_IN_JSON_FORMAT(415, "C001", "Not In Json Format"),
    MISSING_REQUEST(400, "C002", "Missing Request"),
    WRONG_JSON_FORMAT(415, "C003", "Wrong Json Format"),
    WRONG_HTTP_METHOD(405, "C004", "Wrong Http Method"),
    FILE_SAVE_FAILED(400,"C005",  "File Save Failed"),
    WRONG_IMAGE_EXTENSION(400, "C006", "Wrong Image Extension"),
    WRONG_IMAGE_CONTENT_TYPE(400, "C007", "Wrong Image Content Type"),
    MAIL_SEND_FAILED(400, "C008", "Mail Send Failed"),

    AUTHENTICATION_FAILED(401, "E001", "Authentication Failed"),
    TOKEN_REFRESH_FAILED(401, "E002", "Token Refresh"),
    USER_ALREADY_EXIST(409, "E003", "Email Already Exist"),
    NICKNAME_ALREADY_EXIST(409, "E004", "Nickname Already Exist"),
    WRONG_CERTIFY(401, "E005", "Wrong Certify"),
    USER_NOT_FOUND(404, "E006", "User Not Found"),
    TEMPLATE_ALREADY_EXIST(409, "E007", "Template Already Exist"),
    TODOLIST_CHANGE_IMPOSSIBLE(409, "E008", "Todolist Change Impossible");

    private final int status;
    private final String code;
    private final String message;

}
