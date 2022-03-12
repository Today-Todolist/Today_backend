package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.CustomTemplateSubjectRepositoryImpl
import todolist.today.today.domain.template.dao.TemplateDayRepository
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.template.dto.request.TemplateSubjectChangeRequest
import todolist.today.today.domain.template.dto.request.TemplateSubjectCreateRequest
import todolist.today.today.domain.template.dto.request.TemplateSubjectOrderRequest
import todolist.today.today.domain.template.exception.TemplateNotFoundException
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException
import todolist.today.today.domain.template.exception.TemplateSubjectOrderException
import todolist.today.today.domain.user.domain.User

import static todolist.today.today.RequestUtil.*

class TemplateSubjectServiceTest extends Specification {

    private TemplateSubjectService templateSubjectService
    private TemplateRepository templateRepository = Stub(TemplateRepository)
    private TemplateDayRepository templateDayRepository = Stub(TemplateDayRepository)
    private CustomTemplateSubjectRepositoryImpl customTemplateSubjectRepository = Stub(CustomTemplateSubjectRepositoryImpl)
    private TemplateSubjectRepository templateSubjectRepository = Stub(TemplateSubjectRepository)
    private TemplateSortService templateSortService = Stub(TemplateSortService)

    def setup() {
        templateSubjectService = new TemplateSubjectService(
                templateRepository,
                templateDayRepository,
                customTemplateSubjectRepository,
                templateSubjectRepository,
                templateSortService)
    }

    def "test makeTemplateSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TEMPLATE_ID = UUID.randomUUID()
        final UUID TEMPLATE_DAY_ID = UUID.randomUUID()
        final int DAY = 1

        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateRepository.findById(TEMPLATE_ID) >> Optional.of(template)
        template.getUser() >> user
        user.getEmail() >> USER_ID

        templateDayRepository.findByTemplateTemplateIdAndDay(TEMPLATE_ID, DAY)
                >> (exists ? Optional.of(templateDay) : Optional.empty())
        templateDayRepository.save(_) >> templateDay

        templateDay.getTemplateDayId() >> TEMPLATE_DAY_ID
        templateSortService.sortTemplateSubject(templateDay) >> 100

        customTemplateSubjectRepository.getTemplateSubjectLastValue(TEMPLATE_DAY_ID) >> value

        TemplateSubjectCreateRequest request = makeTemplateSubjectCreateRequest(TEMPLATE_ID.toString(), DAY, "")

        when:
        templateSubjectService.makeTemplateSubject(USER_ID, request)

        then:
        noExceptionThrown()

        where:
        value | exists
        1 | true
        1 | false
        2147483500 | true
    }

    def "test makeTemplateSubject TemplateNotFoundException" () {
        given:
        final UUID TEMPLATE_ID = UUID.randomUUID()
        final int DAY = 1

        templateRepository.findById(TEMPLATE_ID) >> Optional.empty()
        TemplateSubjectCreateRequest request = makeTemplateSubjectCreateRequest(TEMPLATE_ID.toString(), DAY, "")

        when:
        templateSubjectService.makeTemplateSubject("", request)

        then:
        thrown(TemplateNotFoundException)
    }

    def "test changeTemplateSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateSubjectChangeRequest request = makeTemplateSubjectChangeRequest("")

        when:
        templateSubjectService.changeTemplateSubject(USER_ID, SUBJECT_ID.toString(), request)

        then:
        noExceptionThrown()
    }

    def "test changeTemplateSubject TemplateSubjectNotFoundException" () {
        given:
        final UUID SUBJECT_ID = UUID.randomUUID()
        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()
        TemplateSubjectChangeRequest request = makeTemplateSubjectChangeRequest("")

        when:
        templateSubjectService.changeTemplateSubject("", SUBJECT_ID.toString(), request)

        then:
        thrown(TemplateSubjectNotFoundException)
    }

    def "test changeTemplateSubjectOrder" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID TEMPLATE_DAY_ID = UUID.randomUUID()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateSubjectOrderRequest request = makeTemplateSubjectOrderRequest(order)
        templateDay.getTemplateDayId() >> TEMPLATE_DAY_ID

        ArrayList<Integer> values = new ArrayList<>()
        for(int i=0; i<2; i++) {
            values.add(value)
            value += add
        }

        customTemplateSubjectRepository
                .getTemplateSubjectValueByOrder(TEMPLATE_DAY_ID, SUBJECT_ID, order) >> values

        when:
        templateSubjectService.changeTemplateSubjectOrder(USER_ID.toString(), SUBJECT_ID.toString(), request)

        then:
        noExceptionThrown()

        where:
        order | add | value
        0 | 0 | 25
        2 | 1 | 25
        2 | 0 | 2147483500
    }

    def "test changeTemplateSubjectOrder TemplateSubjectNotFoundException" () {
        given:
        final UUID SUBJECT_ID = UUID.randomUUID()
        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        templateSubjectService.changeTemplateSubjectOrder("", SUBJECT_ID.toString(), null)

        then:
        thrown(TemplateSubjectNotFoundException)
    }

    def "test changeTemplateSubjectOrder TemplateSubjectOrderException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID TEMPLATE_DAY_ID = UUID.randomUUID()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateSubjectOrderRequest request = makeTemplateSubjectOrderRequest(1)
        templateDay.getTemplateDayId() >> TEMPLATE_DAY_ID

        customTemplateSubjectRepository
                .getTemplateSubjectValueByOrder(TEMPLATE_DAY_ID, SUBJECT_ID, 1) >> { throw new IndexOutOfBoundsException() }

        when:
        templateSubjectService.changeTemplateSubjectOrder(USER_ID.toString(), SUBJECT_ID.toString(), request)

        then:
        thrown(TemplateSubjectOrderException)
    }

    def "test changeTemplateSubjectOrder TemplateSubjectNotFoundException By NullPointerException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID TEMPLATE_DAY_ID = UUID.randomUUID()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        TemplateSubjectOrderRequest request = makeTemplateSubjectOrderRequest(1)
        templateDay.getTemplateDayId() >> TEMPLATE_DAY_ID

        customTemplateSubjectRepository
                .getTemplateSubjectValueByOrder(TEMPLATE_DAY_ID, SUBJECT_ID, 1) >> { throw new NullPointerException() }

        when:
        templateSubjectService.changeTemplateSubjectOrder(USER_ID.toString(), SUBJECT_ID.toString(), request)

        then:
        thrown(TemplateSubjectNotFoundException)
    }

    def "test deleteTemplateSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()

        TemplateTodolistSubject subject = Stub(TemplateTodolistSubject)
        TemplateDay templateDay = Stub(TemplateDay)
        Template template = Stub(Template)
        User user = Stub(User)

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTemplateDay() >> templateDay
        templateDay.getTemplate() >> template
        template.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        templateSubjectService.deleteTemplateSubject(USER_ID.toString(), SUBJECT_ID.toString())

        then:
        noExceptionThrown()
    }

    def "test deleteTemplateSubject TemplateSubjectNotFoundException" () {
        given:
        final UUID SUBJECT_ID = UUID.randomUUID()

        templateSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        templateSubjectService.deleteTemplateSubject("", SUBJECT_ID.toString())

        then:
        thrown(TemplateSubjectNotFoundException)
    }

}