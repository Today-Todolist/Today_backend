package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.InfoService;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

@RestController
@RequiredArgsConstructor
public class InfoController {

    private final AuthenticationFacade authenticationFacade;
    private final InfoService infoService;

    @GetMapping("/info")  @PreAuthorize("isAuthenticated()")
    public MyInfoResponse getMyInfo() {
        return infoService.getMyInfo(authenticationFacade.getUserId());
    }

}
