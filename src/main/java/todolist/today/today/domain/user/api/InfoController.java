package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.InfoService;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class InfoController {

    private final AuthenticationFacade authenticationFacade;
    private final InfoService infoService;

    @GetMapping("/info") @PreAuthorize("isAuthenticated()")
    public MyInfoResponse getMyInfo() {
        return infoService.getMyInfo(authenticationFacade.getUserId());
    }

    @GetMapping("/{email}/info") @PreAuthorize("isAuthenticated()")
    public UserInfoResponse getUserInfo(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        return infoService.getUserInfo(email, authenticationFacade.getUserId());
    }

}
