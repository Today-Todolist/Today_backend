package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.UserInfoService;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController @Validated
@RequiredArgsConstructor
public class UserInfoController {

    private final AuthenticationFacade authenticationFacade;
    private final UserInfoService userInfoService;

    @GetMapping("/info") @PreAuthorize("isAuthenticated()")
    public MyInfoResponse getMyInfo() {
        return userInfoService.getMyInfo(authenticationFacade.getUserId());
    }

    @GetMapping("/{email}/info") @PreAuthorize("isAuthenticated()")
    public UserInfoResponse getUserInfo(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        return userInfoService.getUserInfo(email, authenticationFacade.getUserId());
    }

}
