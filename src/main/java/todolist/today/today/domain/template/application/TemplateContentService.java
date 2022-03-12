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
import todolist.today.today.domain.template.dto.request.TemplateContentOrderRequest;
import todolist.today.today.domain.template.exception.TemplateContentNotFoundException;
import todolist.today.today.domain.template.exception.TemplateContentOrderException;
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException;

import java.util.List;
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
        UUID subjectIdUUID = UUID.fromString(subjectId);

        TemplateTodolistSubject subject = templateSubjectRepository.findById(subjectIdUUID)
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));

        int value = customTemplateContentRepository.getTemplateContentLastValue(subjectIdUUID);
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
                .orElseThrow(() -> new TemplateContentNotFoundException(contentId))
                .updateContent(request.getContent());
    }

    public void changeTemplateContentOrder(String userId, String contentId, TemplateContentOrderRequest request) {
        UUID contentIdUUID = UUID.fromString(contentId);

        TemplateTodolistContent content = templateContentRepository.findById(contentIdUUID)
                .filter(c -> c.getTemplateTodolistSubject().getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateContentNotFoundException(contentId));

        int order = request.getOrder();

        List<Integer> values;
        try {
            values = customTemplateContentRepository
                    .getTemplateContentValueByOrder(content.getTemplateTodolistSubject().getTemplateTodolistSubjectId(), contentIdUUID, order);
        } catch (IndexOutOfBoundsException e) {
            throw new TemplateContentOrderException(order);
        } catch (NullPointerException e) {
            throw new TemplateContentNotFoundException(contentId);
        }

        int value1 = values.get(0);
        int value2 = values.get(1);

        content.updateValue((value1 + value2)/2);
        if (value2 >= 2147483500 || value2 - value1 <= 25) templateSortService.sortTemplateContent(content.getTemplateTodolistSubject());
    }

    public void deleteTemplateContent(String userId, String contentId) {
        TemplateTodolistContent content = templateContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTemplateTodolistSubject().getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateContentNotFoundException(contentId));
        templateContentRepository.delete(content);
    }
}
