package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateContentRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistContent
import todolist.today.today.domain.template.domain.TemplateTodolistSubject


class TemplateSortServiceTest extends Specification {

    private TemplateSortService templateSortService
    private TemplateSubjectRepository templateSubjectRepository = Stub(TemplateSubjectRepository)
    private TemplateContentRepository templateContentRepository = Stub(TemplateContentRepository)

    def setup() {
        templateSortService = new TemplateSortService(templateSubjectRepository, templateContentRepository)
    }

    def "test sortTemplateSubject" () {
        given:
        TemplateDay templateDay = Stub(TemplateDay)
        templateDay.getTemplateTodolistSubjects() >> Arrays.asList(new TemplateTodolistSubject(), new TemplateTodolistSubject())

        when:
        templateSortService.sortTemplateSubject(templateDay)

        then:
        noExceptionThrown()
    }

    def "test sortTemplateContent" () {
        given:
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        subject.getTemplateTodolistContents() >> Arrays.asList(new TemplateTodolistContent(), new TemplateTodolistContent())

        when:
        templateSortService.sortTemplateContent(subject)

        then:
        noExceptionThrown()
    }

}