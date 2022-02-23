package todolist.today.today.domain.template.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.template.domain.Template;

import java.util.UUID;

public interface TemplateRepository extends CrudRepository<Template, UUID> {

    boolean existsByUserEmailAndTitle(String email, String title);

}
