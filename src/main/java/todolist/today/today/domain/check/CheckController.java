package todolist.today.today.domain.check;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.user.dto.request.CheckPasswordRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController @Validated
@RequiredArgsConstructor
public class CheckController {

    private final AuthenticationFacade authenticationFacade;
    private final CheckService checkService;

    @GetMapping("/{email}/email-availability")
    public void checkExistsEmail(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        checkService.checkExistsEmail(email);
    }

    @GetMapping("/{nickname}/nickname-availability")
    public void checkExistsNickname(@PathVariable @Size(min = 1, max = 9) String nickname) {
        checkService.checkExistsNickname(nickname);
    }

    @PostMapping("/validation-password") @PreAuthorize("isAuthenticated()")
    public void checkPassword(@Valid @RequestBody CheckPasswordRequest request) {
        checkService.checkPassword(authenticationFacade.getUserId(), request.getPassword());
    }

    @GetMapping("/{title}/template-availability") @PreAuthorize("isAuthenticated()")
    public void checkExistsTemplateTitle(@PathVariable @Size(min = 1, max = 9) String title) {
        checkService.checkExistsTemplateTitle(authenticationFacade.getUserId(), title);
    }

    @GetMapping("/edit-availability") @PreAuthorize("isAuthenticated()")
    public void checkEditAvailability() {
        checkService.checkEditAvailability(authenticationFacade.getUserId());
    }

}
