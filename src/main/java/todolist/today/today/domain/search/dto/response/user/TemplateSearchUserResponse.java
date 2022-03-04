package todolist.today.today.domain.search.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemplateSearchUserResponse {

    private String email;
    private String nickname;
    private String profile;

}
