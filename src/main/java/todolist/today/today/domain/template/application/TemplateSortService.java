package todolist.today.today.domain.template.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.dao.TemplateSubjectRepository;
import todolist.today.today.domain.template.domain.TemplateDay;
import todolist.today.today.domain.template.domain.TemplateTodolistSubject;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateSortService {

    private final TemplateSubjectRepository templateSubjectRepository;

    public int sortTemplateSubject(TemplateDay templateDay) {
        List<TemplateTodolistSubject> templateTodolistSubjects = templateDay.getTemplateTodolistSubjects();
        templateTodolistSubjects.sort((a, b) -> a.getValue() < b.getValue() ? 1 : 0);

        int value = 0;
        for (TemplateTodolistSubject subject : templateTodolistSubjects) {
            value += 100;
            subject.updateValue(value);
        }
        templateSubjectRepository.saveAll(templateTodolistSubjects);
        return value;
    }

}
