package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.TemplateContentResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateInfoService {

    private final CustomTemplateRepositoryImpl customTemplateRepository;
    private final TemplateRepository templateRepository;

    public List<RandomTemplateResponse> getRandomTemplate(int size) {
        long count = templateRepository.count();
        return customTemplateRepository.getRandomTemplate(size, count);
    }

    public List<MyTemplateResponse> getMyTemplate(String userId) {
        return customTemplateRepository.getMyTemplate(userId);
    }

    public TemplateContentResponse getTemplateContent(String userId, String templateId, int day) {
        return customTemplateRepository.getTemplateContent(userId, templateId, day);
    }

}
