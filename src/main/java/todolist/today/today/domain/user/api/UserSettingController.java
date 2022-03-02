package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.user.application.UserSettingService;
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest;
import todolist.today.today.domain.user.dto.request.ChangePasswordRequest;
import todolist.today.today.domain.user.dto.request.DeleteUserRequest;
import todolist.today.today.domain.user.dto.request.ResetTodolistRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserSettingController {

    private final AuthenticationFacade authenticationFacade;
    private final UserSettingService userSettingService;

    @PutMapping("/profile") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeProfile(@RequestPart(value = "file") MultipartFile file) {
        userSettingService.changeProfile(authenticationFacade.getUserId(), file);
    }

    @PutMapping("/nickname") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeNickname(@Valid @RequestBody ChangeNicknameRequest request) {
        userSettingService.changeNickname(authenticationFacade.getUserId(), request);
    }

    @PutMapping("/password") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userSettingService.changePassword(authenticationFacade.getUserId(), request);
    }

    @PostMapping("/edit-availability/on") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeEditAvailabilityOn() {
        userSettingService.changeEditAvailability(authenticationFacade.getUserId(), true);
    }

    @PostMapping("/edit-availability/off") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeEditAvailabilityOff() {
        userSettingService.changeEditAvailability(authenticationFacade.getUserId(), false);
    }

    @PostMapping("/reset-todolist") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetTodolist(@Valid @RequestBody ResetTodolistRequest request) {
        userSettingService.resetTodolist(authenticationFacade.getUserId(), request);
    }

    @PostMapping("/delete-user") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Valid @RequestBody DeleteUserRequest request) {
        userSettingService.deleteUser(authenticationFacade.getUserId(), request);
    }

}
