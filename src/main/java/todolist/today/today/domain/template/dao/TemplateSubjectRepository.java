package todolist.today.today.domain.template.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.template.domain.TemplateTodolistSubject;

import java.util.UUID;

public interface TemplateSubjectRepository extends CrudRepository<TemplateTodolistSubject, UUID> {
}
