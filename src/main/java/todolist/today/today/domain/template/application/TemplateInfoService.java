package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
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

}
