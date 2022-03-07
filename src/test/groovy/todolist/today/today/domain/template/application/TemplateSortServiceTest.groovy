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
        TemplateTodolistSubject subject1 = new TemplateTodolistSubject()
        TemplateTodolistSubject subject2 = new TemplateTodolistSubject()

        List<TemplateTodolistSubject> subjects = new ArrayList<>()
        subjects.add(subject1)
        subjects.add(subject2)

        TemplateDay templateDay = Stub(TemplateDay)
        templateDay.getTemplateTodolistSubjects() >> subjects

        when:
        templateSortService.sortTemplateSubject(templateDay)

        then:
        noExceptionThrown()
    }

    def "test sortTemplateContent" () {
        given:
        TemplateTodolistContent content1 = new TemplateTodolistContent()
        TemplateTodolistContent content2 = new TemplateTodolistContent()

        List<TemplateTodolistContent> contents = new ArrayList<>()
        contents.add(content1)
        contents.add(content2)

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        subject.getTemplateTodolistContents() >> contents

        when:
        templateSortService.sortTemplateContent(subject)

        then:
        noExceptionThrown()
    }

}