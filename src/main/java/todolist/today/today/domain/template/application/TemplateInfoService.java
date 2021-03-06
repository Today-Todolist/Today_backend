package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateRepository;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.TemplateContentResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateInfoService {

    private final CustomTemplateRepository customTemplateRepository;
    private final TemplateRepository templateRepository;

    public List<RandomTemplateResponse> getRandomTemplate(int size) {
        long count = templateRepository.count();
        if (count == 0) return Collections.emptyList();
        return customTemplateRepository.getRandomTemplate(size, count);
    }

    public List<MyTemplateResponse> getMyTemplate(String userId) {
        return customTemplateRepository.getMyTemplate(userId);
    }

    public TemplateContentResponse getTemplateContent(String userId, String templateId, int day) {
        return customTemplateRepository.getTemplateContent(userId, UUID.fromString(templateId), day);
    }

}
