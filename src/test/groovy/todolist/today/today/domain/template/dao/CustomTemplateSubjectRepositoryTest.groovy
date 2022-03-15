package todolist.today.today.domain.template.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig
import todolist.today.today.global.config.QueryDslConfig

@DataJpaTest
@Import([JpaAuditingConfig, QueryDslConfig])
class CustomTemplateSubjectRepositoryTest extends Specification {

    @Autowired
    JPAQueryFactory jpaQueryFactory
    CustomTemplateSubjectRepository customTemplateSubjectRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    TemplateRepository templateRepository

    @Autowired
    TemplateDayRepository templateDayRepository

    @Autowired
    TemplateSubjectRepository templateSubjectRepository

    @Autowired
    TemplateContentRepository templateContentRepository

    User user
    Template template
    TemplateDay templateDay

    def setup() {
        customTemplateSubjectRepository = new CustomTemplateSubjectRepository(jpaQueryFactory)

        user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        template = templateRepository.save(template)

        templateDay = TemplateDay.builder()
                .template(template)
                .day(1)
                .build()
        templateDay = templateDayRepository.save(templateDay)
    }

    def "test getTemplateSubjectLastValue" () {
        given:
        TemplateTodolistSubject subject1 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(2000)
                .build()
        templateSubjectRepository.save(subject1)

        TemplateTodolistSubject subject2 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(1000)
                .build()
        templateSubjectRepository.save(subject2)

        when:
        int value = customTemplateSubjectRepository.getTemplateSubjectLastValue(templateDay.getTemplateDayId())

        then:
        value == 2000
    }

    def "test getTemplateSubjectValueByOrder" () {
        given:
        TemplateTodolistSubject subject1 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(1000)
                .build()
        templateSubjectRepository.save(subject1)

        TemplateTodolistSubject subject2 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(2000)
                .build()
        templateSubjectRepository.save(subject2)

        TemplateTodolistSubject subject3 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(3000)
                .build()
        templateSubjectRepository.save(subject3)

        when:
        List<Integer> values = customTemplateSubjectRepository
                .getTemplateSubjectValueByOrder(templateDay.getTemplateDayId(), subject3.getTemplateTodolistSubjectId(), 1)

        then:
        values.size() == 2
        values.get(0) == subject1.getValue()
        values.get(1) == subject2.getValue()
    }

}