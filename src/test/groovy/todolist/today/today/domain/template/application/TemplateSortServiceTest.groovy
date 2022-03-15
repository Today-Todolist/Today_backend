package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateContentRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistContent
import todolist.today.today.domain.template.domain.TemplateTodolistSubject


class TemplateSortServiceTest extends Specification {

    TemplateSortService templateSortService
    TemplateSubjectRepository templateSubjectRepository = Stub()
    TemplateContentRepository templateContentRepository = Stub()

    def setup() {
        templateSortService = new TemplateSortService(templateSubjectRepository, templateContentRepository)
    }

    def "test sortTemplateSubject" () {
        given:
        TemplateDay templateDay = Stub()
        TemplateTodolistSubject subject1 = TemplateTodolistSubject.builder()
                .subject("subject1")
                .value(200)
                .build()

        TemplateTodolistSubject subject2 = TemplateTodolistSubject.builder()
                .subject("subject2")
                .value(100)
                .build()

        TemplateTodolistSubject subject3 = TemplateTodolistSubject.builder()
                .subject("subject3")
                .value(300)
                .build()

        templateDay.getTemplateTodolistSubjects() >> Arrays.asList(subject1, subject2, subject3)

        when:
        templateSortService.sortTemplateSubject(templateDay)

        then:
        noExceptionThrown()
    }

    def "test sortTemplateContent" () {
        given:
        TemplateTodolistSubject subject = Stub()
        TemplateTodolistContent content1 = TemplateTodolistContent.builder()
                .content("content1")
                .value(200)
                .build()

        TemplateTodolistContent content2 = TemplateTodolistContent.builder()
                .content("content2")
                .value(100)
                .build()

        TemplateTodolistContent content3 = TemplateTodolistContent.builder()
                .content("content3")
                .value(300)
                .build()

        subject.getTemplateTodolistContents() >> Arrays.asList(content1, content2, content3)

        when:
        templateSortService.sortTemplateContent(subject)

        then:
        noExceptionThrown()
    }

}