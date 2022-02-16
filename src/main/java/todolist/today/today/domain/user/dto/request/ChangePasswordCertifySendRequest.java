package todolist.today.today.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ChangePasswordCertifySendRequest {

    @NotEmpty @Email
    @Size(min = 1, max = 64)
    private String email;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String newPassword;

}
