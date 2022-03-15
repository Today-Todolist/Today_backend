package todolist.today.today.domain.todolist.dto.etc.subject;

import lombok.Getter;
import todolist.today.today.domain.todolist.dto.etc.content.TemplateContentSubjectContentDto;

import java.util.Collections;
import java.util.List;

@Getter
public class TemplateContentSubjectDto {

    private String subject;
    private List<TemplateContentSubjectContentDto> content;

    public TemplateContentSubjectDto(String subject, List<TemplateContentSubjectContentDto> content) {
        this.subject = subject;
        this.content = content.get(0).getContent() != null ? content : Collections.emptyList();
    }

}
