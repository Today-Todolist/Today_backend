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
        TemplateTodolistSubject subject1 = TemplateTodolistSubject.builder()
                .value(value1)
                .build()
        TemplateTodolistSubject subject2 = TemplateTodolistSubject.builder()
                .value(value2)
                .build()

        TemplateDay templateDay = Stub(TemplateDay)
        templateDay.getTemplateTodolistSubjects() >> Arrays.asList(subject1, subject2)

        when:
        templateSortService.sortTemplateSubject(templateDay)

        then:
        noExceptionThrown()

        where:
        value1 | value2
        100 | 200
        200 | 100
    }

    def "test sortTemplateContent" () {
        given:
        TemplateTodolistContent content1 = TemplateTodolistContent.builder()
                .value(value1)
                .build()
        TemplateTodolistContent content2 = TemplateTodolistContent.builder()
                .value(value2)
                .build()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        subject.getTemplateTodolistContents() >> Arrays.asList(content1, content2)

        when:
        templateSortService.sortTemplateContent(subject)

        then:
        noExceptionThrown()

        where:
        value1 | value2
        100 | 200
        200 | 100
    }

}