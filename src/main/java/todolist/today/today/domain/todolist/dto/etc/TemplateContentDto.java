package todolist.today.today.domain.todolist.dto.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.todolist.dto.etc.subject.TemplateContentSubjectDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class TemplateContentDto {

    private int day;
    private List<TemplateContentSubjectDto> subject;

}
