package todolist.today.today.domain.template.dto.response;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MyTemplateResponse {

    private String id;
    private String title;
    private String profile;

    public MyTemplateResponse(UUID id, String title, String profile) {
        this.id = id.toString();
        this.title = title;
        this.profile = profile;
    }

}
