package todolist.today.today.domain.user.dto.response;

import lombok.Getter;
import todolist.today.today.domain.user.dto.response.template.UserInfoTemplateResponse;

import java.util.Collections;
import java.util.List;

@Getter
public class UserInfoResponse {

    private String nickname;
    private String profile;
    private long friendsAmount;
    private int status;
    private List<UserInfoTemplateResponse> templates;

    public UserInfoResponse(String nickname, String profile, long friendsAmount, int status, List<UserInfoTemplateResponse> templates) {
       this.nickname = nickname;
       this.profile = profile;
       this.friendsAmount = friendsAmount;
       this.status = status;
       this.templates = templates.get(0).getId() != null ? templates : Collections.emptyList();
    }

}
