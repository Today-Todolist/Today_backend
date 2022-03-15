package todolist.today.today.domain.todolist.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.todolist.application.TodolistSettingService;
import todolist.today.today.domain.todolist.dto.request.TemplateApplyRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@RestController @Validated
@RequiredArgsConstructor
public class TodolistSettingController {

    private final TodolistSettingService todolistSettingService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/apply-template") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void applyTemplate(@RequestParam("date") @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d") String date,
                              @Valid @RequestBody TemplateApplyRequest request) {
        todolistSettingService.applyTemplate(authenticationFacade.getUserId(), convert(date), request);
    }

}
