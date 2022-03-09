package todolist.today.today.domain.template.dto.response.template.subject;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TemplateContentTemplateSubjectResponse {

    private String id;
    private String value;

    public TemplateContentTemplateSubjectResponse(UUID id, String value) {
        this.id = id == null ? null : id.toString();
        this.value = value;
    }

}
