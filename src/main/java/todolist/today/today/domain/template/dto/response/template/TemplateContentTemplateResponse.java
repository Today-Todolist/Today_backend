package todolist.today.today.domain.template.dto.response.template;

import lombok.Getter;
import todolist.today.today.domain.template.dto.response.template.content.TemplateContentTemplateContentResponse;
import todolist.today.today.domain.template.dto.response.template.subject.TemplateContentTemplateSubjectResponse;

import java.util.Collections;
import java.util.List;

@Getter
public class TemplateContentTemplateResponse {

    private TemplateContentTemplateSubjectResponse subject;
    private List<TemplateContentTemplateContentResponse> content;

    public void resetContent() {
        content = Collections.emptyList();
    }

    public TemplateContentTemplateResponse(TemplateContentTemplateSubjectResponse subject, List<TemplateContentTemplateContentResponse> content) {
        this.subject = subject;
        this.content = content.get(0).getId() != null ? content : Collections.emptyList();
    }

}
