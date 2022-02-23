package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.CheckService;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final CheckService checkService;

    @GetMapping("{email}/email-availability")
    public void checkEmail(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        checkService.checkEmail(email);
    }

    @GetMapping("{nickname}/nickname-availability")
    public void checkNickname(@PathVariable @Size(min = 1, max = 9) String nickname) {
        checkService.checkNickname(nickname);
    }

}
