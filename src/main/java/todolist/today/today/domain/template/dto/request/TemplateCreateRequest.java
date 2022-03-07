package todolist.today.today.domain.template.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TemplateCreateRequest {

    @NotEmpty
    @Size(min = 1, max = 9)
    private String title;

    @Positive @Max(31)
    private int length;

}
