package todolist.today.today.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.user.dto.response.template.UserInfoTemplateResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private String nickname;
    private String profile;
    private long friendsAmount;
    private int status;
    private List<UserInfoTemplateResponse> templates;

}
