package todolist.today.today.domain.template.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.template.application.TemplateInfoService;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController @Validated
@RequiredArgsConstructor
public class TemplateInfoController {

    private final TemplateInfoService templateInfoService;

    @GetMapping("/random-template")
    public List<RandomTemplateResponse> getRandomTemplate(@RequestParam("size") @Positive int size) {
        return templateInfoService.getRandomTemplate(size);
    }

}
