package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.template.application.TemplateSettingService;
import todolist.today.today.domain.template.dto.request.TemplateCreateRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TemplateSettingController {

    private final TemplateSettingService templateSettingService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/template") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTemplate(@Valid @RequestBody TemplateCreateRequest request) {
        templateSettingService.makeTemplate(authenticationFacade.getUserId(), request);
    }

}
