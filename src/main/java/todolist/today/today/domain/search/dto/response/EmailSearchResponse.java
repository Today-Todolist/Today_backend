package todolist.today.today.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailSearchResponse {

    private String email;
    private String nickname;
    private String profile;
    private int status;

}
