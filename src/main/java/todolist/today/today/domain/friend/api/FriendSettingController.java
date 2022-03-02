package todolist.today.today.domain.friend.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    public void applyFriend(@PathVariable @Email @Size(min = 1, max = 64) String email) {
        friendSettingService.applyFriend(email, authenticationFacade.getUserId());
    }

}
