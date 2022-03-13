package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.todolist.application.TodolistClearService;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.NotEmpty;

@RestController @Validated
@RequiredArgsConstructor
@RequestMapping("/clear-todolist")
public class TodolistClearController {

    private final TodolistClearService todolistClearService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/content/{contentId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeToSuccess(@PathVariable("contentId") @NotEmpty String contentId) {
        todolistClearService.changeIsSuccess(authenticationFacade.getUserId(), contentId, true);
    }

    @DeleteMapping("/content/{contentId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeToFail(@PathVariable("contentId") @NotEmpty String contentId) {
        todolistClearService.changeIsSuccess(authenticationFacade.getUserId(), contentId, false);
    }

}
