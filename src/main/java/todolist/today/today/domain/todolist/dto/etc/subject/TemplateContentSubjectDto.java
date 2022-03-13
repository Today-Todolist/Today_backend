package todolist.today.today.domain.todolist.dto.etc.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.todolist.dto.etc.content.TemplateContentSubjectContentDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class TemplateContentSubjectDto {

    private String subject;
    private List<TemplateContentSubjectContentDto> content;

}
