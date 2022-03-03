package todolist.today.today.domain.friend.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.friend.application.FriendInfoService;
import todolist.today.today.domain.friend.dto.response.UserFriendApplyResponse;
import todolist.today.today.domain.friend.dto.response.UserFriendResponse;
import todolist.today.today.global.dto.request.PagingRequest;
import todolist.today.today.global.dto.response.PagingResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@RestController @Validated
@RequiredArgsConstructor
public class FriendInfoController {

    private final AuthenticationFacade authenticationFacade;
    private final FriendInfoService friendInfoService;

    @GetMapping("/{email}/friends") @PreAuthorize("isAuthenticated()")
    public PagingResponse<UserFriendResponse> getUserFriends(@PathVariable @Email @Size(min = 1, max = 64) String email,
                                                             @RequestParam("page") @Min(0) int page,
                                                             @RequestParam("size") @Positive int size) {
        return friendInfoService.getUserFriends(email, authenticationFacade.getUserId(), new PagingRequest(page, size));
    }

    @GetMapping("/friend-applys") @PreAuthorize("isAuthenticated()")
    public PagingResponse<UserFriendApplyResponse> getUserFriendsApply(@RequestParam("page") @Min(0) int page,
                                                                       @RequestParam("size") @Positive int size) {
        return friendInfoService.getUserFriendsApply(authenticationFacade.getUserId(), new PagingRequest(page, size));
    }

}
