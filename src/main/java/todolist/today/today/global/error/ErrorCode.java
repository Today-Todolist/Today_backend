package todolist.today.today.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "S001", "Internal Server Error"),
    CREATE_RANDOM_IMAGE_FAILED(500, "S002", "Create Random Image Failed"),
    FILE_DELETE_FAILED(500, "S003", "Local File Delete Failed"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),
    UNAUTHORIZED_REQUEST(401, "A002", "Unauthorized Request"),

    NOT_IN_JSON_FORMAT(415, "C001", "Not In Json Format"),
    MISSING_REQUEST(400, "C002", "Missing Request"),
    WRONG_JSON_FORMAT(415, "C003", "Wrong Json Format"),
    WRONG_HTTP_METHOD(405, "C004", "Wrong Http Method"),
    FILE_SAVE_FAILED(400, "C005", "File Save Failed"),
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
    TODOLIST_CHANGE_IMPOSSIBLE(409, "E008", "Todolist Change Impossible"),
    FRIEND_ALREADY_EXIST(409, "E009", "Friend Already Exist"),
    FRIEND_APPLY_ALREADY_EXIST(409, "E010", "Friend Apply Already Exist"),
    TEMPLATE_NOT_FOUND(404, "E011", "Template Not Found"),
    TEMPLATE_SUBJECT_NOT_FOUND(404, "E012", "Template Subject Not Found"),
    TEMPLATE_SUBJECT_ORDER_OUT_OF_RANGE(409, "E013", "Template Subject Order Out Of Range"),
    TEMPLATE_CONTENT_NOT_FOUND(404, "E014", "Template Content Not Found"),
    TEMPLATE_CONTENT_ORDER_OUT_OF_RANGE(409, "E015", "Template Content Order Out Of Range"),
    TODOLIST_SUBJECT_NOT_FOUND(404, "E016", "Todolist Subject Not Found"),
    TODOLIST_SUBJECT_ORDER_OUT_OF_RANGE(409, "E017", "Todolist Subject Order Out Of Range"),
    TODOLIST_CONTENT_NOT_FOUND(404, "E018", "Todolist Content Not Found"),
    TODOLIST_CONTENT_ORDER_OUT_OF_RANGE(409, "E019", "Todolist Content Order Out Of Range"),
    FRIEND_APPLY_NOT_FOUND(404, "E020", "Friend Apply Not Found");

    private final int status;
    private final String code;
    private final String message;

}
