package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.user.application.SettingService;
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SettingController {

    private final AuthenticationFacade authenticationFacade;
    private final SettingService settingService;

    @PutMapping("/profile") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeProfile(@RequestPart(value = "file") MultipartFile file) {
        settingService.changeProfile(file, authenticationFacade.getUserId());
    }

    @PutMapping("/nickname") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeNickname(@Valid @RequestBody ChangeNicknameRequest request) {
        settingService.changeNickname(request, authenticationFacade.getUserId());
    }

}
