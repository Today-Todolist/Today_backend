package todolist.today.today.domain.template.dto.response.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.template.dto.response.template.content.TemplateContentTemplateContentResponse;
import todolist.today.today.domain.template.dto.response.template.subject.TemplateContentTemplateSubjectResponse;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class TemplateContentTemplateResponse {

    private TemplateContentTemplateSubjectResponse subject;
    private List<TemplateContentTemplateContentResponse> content;

    public void resetContent() {
        content = Collections.emptyList();
    }

}
