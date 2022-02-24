package todolist.today.today.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.user.dto.response.template.MyInfoTemplateResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyInfoResponse {

    private String email;
    private String nickname;
    private String profile;
    private long friendsAmount;
    private List<MyInfoTemplateResponse> templates;

}
