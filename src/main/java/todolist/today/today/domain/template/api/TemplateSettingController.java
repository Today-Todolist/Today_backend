package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import todolist.today.today.domain.template.application.TemplateSettingService;
import todolist.today.today.domain.template.dto.request.TemplateCreateRequest;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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

    @PutMapping("/template-profile/{templateId}") @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeProfile(@PathVariable("templateId") @NotEmpty String templateId,
                              @RequestPart("profile") MultipartFile profile) {
        templateSettingService.changeProfile(authenticationFacade.getUserId(), templateId, profile);
    }

}
