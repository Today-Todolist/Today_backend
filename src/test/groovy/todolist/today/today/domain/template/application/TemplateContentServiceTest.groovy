package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.CustomTemplateContentRepositoryImpl
import todolist.today.today.domain.template.dao.TemplateContentRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistContent
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.template.dto.request.TemplateContentChangeRequest
import todolist.today.today.domain.template.dto.request.TemplateContentCreateRequest
import todolist.today.today.domain.template.dto.request.TemplateContentOrderRequest
import todolist.today.today.domain.template.exception.TemplateContentNotFoundException
import todolist.today.today.domain.template.exception.TemplateContentOrderException
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException
import todolist.today.today.domain.user.domain.User

import static todolist.today.today.RequestUtil.*

class TemplateContentServiceTest extends Specification {

    private TemplateContentService templateContentService
    private TemplateSubjectRepository templateSubjectRepository = Stub(TemplateSubjectRepository)
    private CustomTemplateContentRepositoryImpl customTemplateContentRepository = Stub(CustomTemplateContentRepositoryImpl)
    private TemplateContentRepository templateContentRepository = Stub(TemplateContentRepository)
    private TemplateSortService templateSortService = Stub(TemplateSortService)

    def setup() {
        templateContentService = new TemplateContentService(
                templateSubjectRepository,
                customTemplateContentRepository,
                templateContentRepository,
                templateSortService)
    }

    def "test makeTemplateContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TemplateContentCreateRequest request = makeTemplateContentCreateRequest(SUBJECT_ID.toString(), "")

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        customTemplateContentRepository.getTemplateContentLastValue(SUBJECT_ID) >> value

        when:
        templateContentService.makeTemplateContent(USER_ID, request)

        then:
        noExceptionThrown()

        where:
        value << [100, 2147483500]
    }

    def "test makeTemplateContent TemplateSubjectNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TemplateContentCreateRequest request = makeTemplateContentCreateRequest(SUBJECT_ID.toString(), "")

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        templateContentService.makeTemplateContent(USER_ID, request)

        then:
        thrown(TemplateSubjectNotFoundException)
    }

    def "test changeTemplateContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()
        TemplateContentChangeRequest request = makeTemplateContentChangeRequest("")

        TemplateTodolistContent content = Stub(TemplateTodolistContent)
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        templateContentService.changeTemplateContent(USER_ID, CONTENT_ID.toString(), request)

        then:
        noExceptionThrown()
    }

    def "test changeTemplateContent TemplateContentNotFoundException" () {
        given:
        final UUID CONTENT_ID = UUID.randomUUID()
        TemplateContentChangeRequest request = makeTemplateContentChangeRequest("")

        templateContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        templateContentService.changeTemplateContent("", CONTENT_ID.toString(), request)

        then:
        thrown(TemplateContentNotFoundException)
    }

    def "test changeTemplateContentOrder" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID CONTENT_ID = UUID.randomUUID()

        TemplateTodolistContent content = Stub(TemplateTodolistContent)
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateContentOrderRequest request = makeTemplateContentOrderRequest(order)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateTodolistSubjectId() >> SUBJECT_ID

        ArrayList<Integer> values = new ArrayList<>()
        for(int i=0; i<2; i++) {
            values.add(value)
            value += add
        }

        customTemplateContentRepository
                .getTemplateContentValueByOrder(SUBJECT_ID, CONTENT_ID, order) >> values

        when:
        templateContentService.changeTemplateContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        noExceptionThrown()

        where:
        order | add | value
        0 | 0 | 25
        2 | 1 | 25
        2 | 0 | 2147483500
    }

    def "test changeTemplateContentOrder TemplateContentNotFoundException" () {
        given:
        final UUID CONTENT_ID = UUID.randomUUID()
        templateContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        templateContentService.changeTemplateContentOrder(null, CONTENT_ID.toString(), null)

        then:
        thrown(TemplateContentNotFoundException)
    }

    def "test changeTemplateContentOrder TemplateContentOrderException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID CONTENT_ID = UUID.randomUUID()

        TemplateTodolistContent content = Stub(TemplateTodolistContent)
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateContentOrderRequest request = makeTemplateContentOrderRequest(1)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateTodolistSubjectId() >> SUBJECT_ID

        customTemplateContentRepository
                .getTemplateContentValueByOrder(SUBJECT_ID, CONTENT_ID, 1) >> { throw new IndexOutOfBoundsException() }

        when:
        templateContentService.changeTemplateContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        thrown(TemplateContentOrderException)
    }

    def "test changeTemplateContentOrder TemplateContentNotFoundException By NullPointerException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID CONTENT_ID = UUID.randomUUID()

        TemplateTodolistContent content = Stub(TemplateTodolistContent)
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateContentOrderRequest request = makeTemplateContentOrderRequest(1)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateTodolistSubjectId() >> SUBJECT_ID

        customTemplateContentRepository
                .getTemplateContentValueByOrder(SUBJECT_ID, CONTENT_ID, 1) >> { throw new NullPointerException() }

        when:
        templateContentService.changeTemplateContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        thrown(TemplateContentNotFoundException)
    }

    def "test deleteTemplateContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()
        TemplateTodolistContent content = Stub(TemplateTodolistContent)
        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTemplateTodolistSubject() >> subject
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        templateContentService.deleteTemplateContent(USER_ID, CONTENT_ID.toString())

        then:
        noExceptionThrown()
    }

    def "test deleteTemplateContent TemplateContentNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        templateContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        templateContentService.deleteTemplateContent(USER_ID, CONTENT_ID.toString())

        then:
        thrown(TemplateContentNotFoundException)
    }

}