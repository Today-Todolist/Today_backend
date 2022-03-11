package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.todolist.application.TodolistSubjectService;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectChangeRequest;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectCreateRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController @Validated
@RequiredArgsConstructor
@RequestMapping("/todolist")
public class TodolistSubjectController {

    private final TodolistSubjectService todolistSubjectService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/subject") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTodolistSubject(@Valid @RequestBody TodolistSubjectCreateRequest request) {
        todolistSubjectService.makeTodolistSubject(authenticationFacade.getUserId(), request);
    }

    @PostMapping("/subject/{subjectId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTodolistSubject(@PathVariable("subjectId") @NotEmpty String subjectId,
                                      @Valid @RequestBody TodolistSubjectChangeRequest request) {
        todolistSubjectService.changeTodolistSubject(authenticationFacade.getUserId(), subjectId, request);
    }

}
