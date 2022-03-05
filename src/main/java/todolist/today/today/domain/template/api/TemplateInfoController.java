package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.template.application.TemplateInfoService;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.TemplateContentResponse;
import todolist.today.today.global.security.service.AuthenticationFacade;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController @Validated
@RequiredArgsConstructor
public class TemplateInfoController {

    private final TemplateInfoService templateInfoService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/random-template")
    public List<RandomTemplateResponse> getRandomTemplate(@RequestParam("size") @Positive int size) {
        return templateInfoService.getRandomTemplate(size);
    }

    @GetMapping("/templates") @PreAuthorize("isAuthenticated()")
    public List<MyTemplateResponse> getMyTemplate() {
        return templateInfoService.getMyTemplate(authenticationFacade.getUserId());
    }

    @GetMapping("/template/{templateId}") @PreAuthorize("isAuthenticated()")
    public TemplateContentResponse getTemplateContent(@PathVariable("templateId") @Size(min = 36, max = 36) String templateId,
                                                      @RequestParam("day") @Positive int day) {
        return templateInfoService.getTemplateContent(authenticationFacade.getUserId(), templateId, day);
    }

}
