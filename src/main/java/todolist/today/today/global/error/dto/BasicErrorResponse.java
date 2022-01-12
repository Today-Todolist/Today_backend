package todolist.today.today.global.error.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@JsonPropertyOrder({ "timestamp", "status", "code", "message" })
public class BasicErrorResponse {

    @JsonProperty("timestamp")
    private final ZonedDateTime timestamp;

    @JsonProperty("status")
    private final int status;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    protected BasicErrorResponse(ErrorCode errorCode) {
        this.timestamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static BasicErrorResponse from(ErrorCode errorCode) {
        return new BasicErrorResponse(errorCode);
    }

    public static BasicErrorResponse from(BasicException e) {
        return new BasicErrorResponse(e.getErrorCode());
    }

}
