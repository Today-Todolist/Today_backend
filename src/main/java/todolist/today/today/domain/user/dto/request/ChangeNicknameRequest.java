package todolist.today.today.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ChangeNicknameRequest {

    @NotEmpty
    @Size(min = 1, max = 9)
    private String newNickname;

}
