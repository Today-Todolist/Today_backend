package todolist.today.today.domain.template.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class TemplateContentOrderRequest {

    @Min(0) @Max(20)
    private int order;

}
