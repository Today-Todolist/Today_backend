package todolist.today.today.domain.template.dto.response.template.content;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TemplateContentTemplateContentResponse {

    private String id;
    private String value;

    public TemplateContentTemplateContentResponse(UUID id, String value) {
        this.id = id.toString();
        this.value = value;
    }

}
