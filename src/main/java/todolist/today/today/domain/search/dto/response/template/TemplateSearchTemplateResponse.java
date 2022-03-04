package todolist.today.today.domain.search.dto.response.template;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TemplateSearchTemplateResponse {

    private String id;
    private String title;
    private String profile;

    public TemplateSearchTemplateResponse(UUID id, String title, String profile) {
        this.id = id.toString();
        this.title = title;
        this.profile = profile;
    }

}
