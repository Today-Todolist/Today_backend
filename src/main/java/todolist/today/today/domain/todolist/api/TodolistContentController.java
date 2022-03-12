package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.todolist.application.TodolistContentService;
import todolist.today.today.domain.todolist.dto.request.TodolistContentCreateRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/todolist")
public class TodolistContentController {

    private final TodolistContentService todolistContentService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/content") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTodolistContent(@Valid @RequestBody TodolistContentCreateRequest request) {
        todolistContentService.makeTodolistSubject(authenticationFacade.getUserId(), request);
    }

}
