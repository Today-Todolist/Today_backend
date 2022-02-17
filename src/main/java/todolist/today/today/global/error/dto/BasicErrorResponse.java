package todolist.today.today.global.error.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@JsonPropertyOrder({ "timestamp", "status", "code", "message" })
public class BasicErrorResponse {

    @JsonProperty("timestamp")
    private final String timestamp;

    @JsonProperty("status")
    private final int status;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    public BasicErrorResponse(ErrorCode errorCode) {
        this.timestamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
