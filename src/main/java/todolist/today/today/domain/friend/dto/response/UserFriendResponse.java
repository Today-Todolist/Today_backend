package todolist.today.today.domain.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFriendResponse {

    private String email;
    private String nickname;
    private String profile;
    private int status;

}
