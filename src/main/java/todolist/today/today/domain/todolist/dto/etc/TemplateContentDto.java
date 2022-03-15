package todolist.today.today.domain.todolist.dto.etc;

import lombok.Getter;
import todolist.today.today.domain.todolist.dto.etc.subject.TemplateContentSubjectDto;

import java.util.Collections;
import java.util.List;

@Getter
public class TemplateContentDto {

    private int day;
    private List<TemplateContentSubjectDto> subject;

    public TemplateContentDto(int day, List<TemplateContentSubjectDto> subject) {
        this.day = day;
        this.subject = subject.get(0).getSubject() != null ? subject : Collections.emptyList();
    }

}
