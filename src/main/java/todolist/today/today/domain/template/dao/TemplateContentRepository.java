package todolist.today.today.domain.template.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.template.domain.TemplateTodolistContent;

import java.util.UUID;

public interface TemplateContentRepository extends CrudRepository<TemplateTodolistContent, UUID> {
}
