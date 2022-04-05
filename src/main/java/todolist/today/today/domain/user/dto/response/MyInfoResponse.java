package todolist.today.today.domain.user.dto.response;

import lombok.Getter;
import todolist.today.today.domain.user.dto.response.template.MyInfoTemplateResponse;

import java.util.Collections;
import java.util.List;

@Getter
public class MyInfoResponse {

    private String email;
    private String nickname;
    private String profile;
    private long friendsAmount;
    private List<MyInfoTemplateResponse> templates;

    public MyInfoResponse(String email, String nickname, String profile, long friendsAmount, List<MyInfoTemplateResponse> templates) {
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.friendsAmount = friendsAmount;
        this.templates = templates.get(0).getId() != null ? templates : Collections.emptyList();
    }

}
