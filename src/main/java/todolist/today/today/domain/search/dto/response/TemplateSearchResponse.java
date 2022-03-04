package todolist.today.today.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.search.dto.response.template.TemplateSearchTemplateResponse;
import todolist.today.today.domain.search.dto.response.user.TemplateSearchUserResponse;

@Getter
@AllArgsConstructor
public class TemplateSearchResponse {

    private TemplateSearchUserResponse user;
    private TemplateSearchTemplateResponse template;

}