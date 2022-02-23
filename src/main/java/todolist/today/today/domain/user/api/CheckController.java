package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.user.application.CheckService;
import todolist.today.today.domain.user.dto.request.CheckPasswordRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class CheckController {

    private final AuthenticationFacade authenticationFacade;
    private final CheckService checkService;

    @GetMapping("/{email}/email-availability")
    public void checkEmail(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        checkService.checkEmail(email);
    }

    @GetMapping("/{nickname}/nickname-availability")
    public void checkNickname(@PathVariable @Size(min = 1, max = 9) String nickname) {
        checkService.checkNickname(nickname);
    }

    @PostMapping("/validation-password") @PreAuthorize("isAuthenticated()")
    public void checkPassword(@Valid @RequestBody CheckPasswordRequest request) {
        checkService.checkPassword(authenticationFacade.getUserId(), request.getPassword());
    }

    @GetMapping("/{title}/template-availability") @PreAuthorize("isAuthenticated()")
    public void checkTemplateTitle(@PathVariable @Size(min = 1, max = 9) String title) {
        checkService.checkTemplateTitle(authenticationFacade.getUserId(), title);
    }

}
