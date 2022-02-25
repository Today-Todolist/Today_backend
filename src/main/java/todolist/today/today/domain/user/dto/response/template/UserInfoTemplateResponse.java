package todolist.today.today.domain.user.dto.response.template;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserInfoTemplateResponse {

    private String id;
    private String title;
    private String profile;

    public UserInfoTemplateResponse(UUID id, String title, String profile) {
        this.id = id.toString();
        this.title = title;
        this.profile = profile;
    }
}
