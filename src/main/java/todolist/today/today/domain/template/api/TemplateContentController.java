package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import todolist.today.today.domain.template.application.TemplateContentService;
import todolist.today.today.domain.template.dto.request.TemplateContentChangeRequest;
import todolist.today.today.domain.template.dto.request.TemplateContentCreateRequest;
import todolist.today.today.domain.template.dto.request.TemplateContentOrderRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController @Validated
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateContentController {

    private final TemplateContentService templateContentService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/content") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTemplateContent(@Valid @RequestBody TemplateContentCreateRequest request) {
        templateContentService.makeTemplateContent(authenticationFacade.getUserId(), request);
    }

    @PutMapping("/content/{contentId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTemplateContent(@PathVariable("contentId") @NotEmpty String contentId,
                                      @Valid @RequestBody TemplateContentChangeRequest request) {
        templateContentService.changeTemplateContent(authenticationFacade.getUserId(), contentId, request);
    }

    @PutMapping("/content-order/{contentId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTemplateContentOrder(@PathVariable("contentId") @NotEmpty String contentId,
                                           @Valid @RequestBody TemplateContentOrderRequest request) {
        templateContentService.changeTemplateContentOrder(authenticationFacade.getUserId(), contentId, request);
    }

}
