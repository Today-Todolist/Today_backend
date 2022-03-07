package todolist.today.today.domain.template.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TemplateSubjectCreateRequest {

    @NotEmpty
    private String id;

    @Positive @Max(31)
    private int day;

    @NotEmpty @Size(min = 1, max = 31)
    private String subject;

}
