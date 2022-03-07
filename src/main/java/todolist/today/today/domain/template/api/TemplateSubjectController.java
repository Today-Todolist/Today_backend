package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.template.application.TemplateSubjectService;
import todolist.today.today.domain.template.dto.request.TemplateSubjectChangeRequest;
import todolist.today.today.domain.template.dto.request.TemplateSubjectCreateRequest;
import todolist.today.today.domain.template.dto.request.TemplateSubjectOrderRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController @Validated
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateSubjectController {

    private final TemplateSubjectService templateSubjectService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/subject") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTemplateSubject(@Valid @RequestBody TemplateSubjectCreateRequest request) {
        templateSubjectService.makeTemplateSubject(authenticationFacade.getUserId(), request);
    }
    
    @PutMapping("/template/subject/{subjectId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTemplateSubject(@PathVariable("subjectId") @NotEmpty String subjectId,
                                      @Valid @RequestBody TemplateSubjectChangeRequest request) {
        templateSubjectService.changeTemplateSubject(authenticationFacade.getUserId(), subjectId, request);
    }

    @PutMapping("/template/subject-order/{subjectId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTemplateSubjectOrder(@PathVariable("subjectId") @NotEmpty String subjectId,
                                           @Valid @RequestBody TemplateSubjectOrderRequest request) {
        templateSubjectService.changeTemplateSubjectOrder(authenticationFacade.getUserId(), subjectId, request);
    }

    @DeleteMapping("/template/subject/{subjectId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTemplateSubject(@PathVariable("subjectId") @NotEmpty String subjectId) {
        templateSubjectService.deleteTemplateSubject(authenticationFacade.getUserId(), subjectId);
    }

}
