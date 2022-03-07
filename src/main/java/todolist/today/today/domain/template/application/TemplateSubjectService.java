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
import todolist.today.today.domain.template.dto.request.TemplateSubjectOrderRequest;
import todolist.today.today.domain.template.exception.TemplateNotFoundException;
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException;
import todolist.today.today.domain.template.exception.TemplateSubjectOrderException;

import java.util.List;
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
        if (value >= 2147483500) value = templateSortService.sortTemplateSubject(templateDay);

        TemplateTodolistSubject subject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject(request.getSubject())
                .value(value + 100)
                .build();
        templateSubjectRepository.save(subject);
    }

    public void changeTemplateSubject(String userId, String subjectId, TemplateSubjectChangeRequest request) {
        templateSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId))
                .updateSubject(request.getSubject());
    }

    public void changeTemplateSubjectOrder(String userId, String subjectId, TemplateSubjectOrderRequest request) {
        TemplateTodolistSubject subject = templateSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));

        int order = request.getOrder();
        List<Integer> values = customTemplateSubjectRepository.getTemplateSubjectValueByOrder(subjectId, request.getOrder());
        if (values.size() == 0) throw new TemplateSubjectOrderException(order);
        else if (values.size() == 1) {
            int value = values.get(0);
            if (order == 0) {
                subject.updateValue(value/2);
                if (value <= 25) templateSortService.sortTemplateSubject(subject.getTemplateDay());
            } else if (value >= 2147483500) subject.updateValue(templateSortService.sortTemplateSubject(subject.getTemplateDay()) + 100);
            else subject.updateValue(value + 100);
        }
        else {
            int value1 = values.get(0);
            int value2 = values.get(1);
            subject.updateValue((value1 + value2) / 2);
            if (value2 - value1 <= 25) templateSortService.sortTemplateSubject(subject.getTemplateDay());
        }
    }

    public void deleteTemplateSubject(String userId, String subjectId) {
        TemplateTodolistSubject subject = templateSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(s -> s.getTemplateDay().getTemplate().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));
        templateSubjectRepository.delete(subject);
    }

}
