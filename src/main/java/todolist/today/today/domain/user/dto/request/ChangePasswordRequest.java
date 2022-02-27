package todolist.today.today.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String newPassword;

}
