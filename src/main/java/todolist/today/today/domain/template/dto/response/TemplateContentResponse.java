package todolist.today.today.domain.template.dto.response;

import lombok.Getter;
import todolist.today.today.domain.template.dto.response.template.TemplateContentTemplateResponse;

import java.util.Collections;
import java.util.List;

@Getter
public class TemplateContentResponse {

    private String title;
    private String profile;
    private int length;
    private int status;
    private List<TemplateContentTemplateResponse> list;

    public TemplateContentResponse(String title, String profile, int length, int status, List<TemplateContentTemplateResponse> list) {
        this.title = title;
        this.profile = profile;
        this.length = length;
        this.status = status;
        this.list = list.get(0).getContent() != null ? list : Collections.emptyList();
    }

}
