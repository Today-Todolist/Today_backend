package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateContentRepositoryImpl;
import todolist.today.today.domain.template.dao.TemplateContentRepository;
import todolist.today.today.domain.template.dao.TemplateSubjectRepository;
import todolist.today.today.domain.template.domain.TemplateTodolistContent;
import todolist.today.today.domain.template.domain.TemplateTodolistSubject;
import todolist.today.today.domain.template.dto.request.TemplateContentChangeRequest;
import todolist.today.today.domain.template.dto.request.TemplateContentCreateRequest;
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateContentService {

    private final TemplateSubjectRepository templateSubjectRepository;
    private final CustomTemplateContentRepositoryImpl customTemplateContentRepository;
    private final TemplateContentRepository templateContentRepository;
    private final TemplateSortService templateSortService;

    public void makeTemplateContent(String userId, TemplateContentCreateRequest request) {
        String subjectId = request.getId();
        TemplateTodolistSubject subject = templateSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));

        int value = customTemplateContentRepository.getTemplateContentLastValue(subjectId);
        if (value >= 2147483500) value = templateSortService.sortTemplateContent(subject);
        TemplateTodolistContent content = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content(request.getContent())
                .value(value + 100)
                .build();
        templateContentRepository.save(content);
    }

    public void changeTemplateContent(String userId, String contentId, TemplateContentChangeRequest request) {
        templateContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTemplateTodolistSubject().getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(contentId))
                .updateContent(request.getContent());
    }

}
