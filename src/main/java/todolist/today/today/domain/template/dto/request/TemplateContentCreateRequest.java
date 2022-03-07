package todolist.today.today.domain.template.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TemplateContentCreateRequest {

    @NotEmpty
    private String id;

    @NotEmpty @Size(min = 1, max = 31)
    private String content;

}
