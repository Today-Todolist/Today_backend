package todolist.today.today.domain.template.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.template.dto.response.template.RandomTemplateTemplateResponse;
import todolist.today.today.domain.template.dto.response.user.RandomTemplateUserResponse;

@Getter
@AllArgsConstructor
public class RandomTemplateResponse {

    private RandomTemplateUserResponse user;
    private RandomTemplateTemplateResponse template;

}
