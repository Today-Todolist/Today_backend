package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.TemplateContentResponse;
import todolist.today.today.domain.template.exception.TemplateContentNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateInfoService {

    private final CustomTemplateRepositoryImpl customTemplateRepository;
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
        if (!templateRepository.existsById(UUID.fromString(templateId))) throw new TemplateContentNotFoundException(templateId);
        return customTemplateRepository.getTemplateContent(userId, templateId, day);
    }

}
