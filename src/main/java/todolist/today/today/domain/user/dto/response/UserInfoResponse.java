package todolist.today.today.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;
import todolist.today.today.domain.user.dto.response.template.UserInfoTemplateResponse;

import java.util.List;

@Getter
public class UserInfoResponse {

    private String nickname;
    private String profile;
    private long friendsAmount;
    private int status;

    @Setter
    private List<UserInfoTemplateResponse> templates;

    public UserInfoResponse(String nickname, String profile, long friendsAmount, int status) {
        this.nickname = nickname;
        this.profile = profile;
        this.friendsAmount = friendsAmount;
        this.status = status;
    }

}
