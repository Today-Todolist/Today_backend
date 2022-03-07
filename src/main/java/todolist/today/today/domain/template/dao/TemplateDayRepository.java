package todolist.today.today.domain.template.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.template.domain.TemplateDay;

import java.util.Optional;
import java.util.UUID;

public interface TemplateDayRepository extends CrudRepository<TemplateDay, UUID> {

    Optional<TemplateDay> findByTemplateTemplateIdAndDay(UUID id, int day);

}
