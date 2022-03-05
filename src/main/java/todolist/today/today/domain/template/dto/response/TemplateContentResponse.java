package todolist.today.today.domain.template.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.template.dto.response.template.TemplateContentTemplateResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class TemplateContentResponse {

    private String title;
    private String profile;
    private int length;
    private int status;
    private List<TemplateContentTemplateResponse> list;

}
