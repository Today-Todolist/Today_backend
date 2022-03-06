package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.CustomTemplateSubjectRepositoryImpl;
import todolist.today.today.domain.template.dao.TemplateDayRepository;
import todolist.today.today.domain.template.dao.TemplateRepository;
import todolist.today.today.domain.template.dao.TemplateSubjectRepository;
import todolist.today.today.domain.template.domain.Template;
import todolist.today.today.domain.template.domain.TemplateDay;
import todolist.today.today.domain.template.domain.TemplateTodolistSubject;
import todolist.today.today.domain.template.dto.request.TemplateSubjectChangeRequest;
import todolist.today.today.domain.template.dto.request.TemplateSubjectCreateRequest;
import todolist.today.today.domain.template.exception.TemplateNotFoundException;
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateSubjectService {

    private final TemplateRepository templateRepository;
    private final TemplateDayRepository templateDayRepository;
    private final CustomTemplateSubjectRepositoryImpl customTemplateSubjectRepository;
    private final TemplateSubjectRepository templateSubjectRepository;
    private final TemplateSortService templateSortService;

    public void makeTemplateSubject(String userId, TemplateSubjectCreateRequest request) {
        String templateId = request.getId();
        UUID templateUUID = UUID.fromString(templateId);

        Template template = templateRepository.findById(templateUUID)
                .filter(t -> t.getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateNotFoundException(templateId));

        TemplateDay templateDay = templateDayRepository.findByTemplateTemplateIdAndDay(templateUUID, request.getDay())
                .orElseGet(() ->
                    templateDayRepository.save(TemplateDay.builder()
                            .template(template)
                            .day(request.getDay())
                            .build()));
        int value = customTemplateSubjectRepository.getTemplateSubjectLastValue(templateDay.getTemplateDayId());
        if (value >= 2147483500) value = templateSortService.sortTemplateSort(templateDay);

        TemplateTodolistSubject templateSubject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject(request.getSubject())
                .value(value + 100)
                .build();
        templateSubjectRepository.save(templateSubject);
    }

    public void changeTemplateSubject(String userId, String subjectId, TemplateSubjectChangeRequest request) {
        TemplateTodolistSubject subject = templateSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));
        subject.updateSubject(request.getSubject());
    }

}
