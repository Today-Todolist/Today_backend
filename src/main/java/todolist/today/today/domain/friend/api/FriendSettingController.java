package todolist.today.today.domain.friend.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.friend.application.FriendSettingService;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
public class FriendSettingController {

    private final AuthenticationFacade authenticationFacade;
    private final FriendSettingService friendSettingService;

    @PostMapping("/friend-apply/{email}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeFriendApply(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        friendSettingService.makeFriendApply(email, authenticationFacade.getUserId());
    }

    @DeleteMapping("/friend/{email}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriend(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        friendSettingService.deleteFriend(email, authenticationFacade.getUserId());
    }

    @PostMapping("/friend/{email}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeFriend(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        friendSettingService.makeFriend(email, authenticationFacade.getUserId());
    }

    @DeleteMapping("/friend-apply/{email}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendApply(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        friendSettingService.deleteFriendApply(email, authenticationFacade.getUserId());
    }

}
