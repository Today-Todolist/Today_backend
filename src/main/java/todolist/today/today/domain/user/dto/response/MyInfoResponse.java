package todolist.today.today.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;
import todolist.today.today.domain.user.dto.response.template.MyInfoTemplateResponse;

import java.util.List;

@Getter
public class MyInfoResponse {

    private String email;
    private String nickname;
    private String profile;
    private long friendsAmount;

    @Setter
    private List<MyInfoTemplateResponse> templates;

    public MyInfoResponse(String email, String nickname, String profile, long friendsAmount) {
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.friendsAmount = friendsAmount;
    }

}
