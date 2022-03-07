package todolist.today.today.domain.template.dto.response.template;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RandomTemplateTemplateResponse {

    private String id;
    private String title;
    private String profile;

    public RandomTemplateTemplateResponse(UUID id, String title, String profile) {
        this.id = id.toString();
        this.title = title;
        this.profile = profile;
    }

}
