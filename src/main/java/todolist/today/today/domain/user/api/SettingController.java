package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.user.application.SettingService;
import todolist.today.today.global.security.service.AuthenticationFacade;

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

}
