package todolist.today.today.domain.template.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistContent
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig
import todolist.today.today.global.config.QueryDslConfig

@DataJpaTest
@Import([JpaAuditingConfig, QueryDslConfig])
class CustomTemplateContentRepositoryImplTest extends Specification {

    @Autowired
    JPAQueryFactory jpaQueryFactory
    CustomTemplateContentRepositoryImpl customTemplateContentRepository

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
    TemplateTodolistSubject subject

    def setup() {
        customTemplateContentRepository = new CustomTemplateContentRepositoryImpl(jpaQueryFactory)

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

        subject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("subject")
                .value(100)
                .build()
        subject = templateSubjectRepository.save(subject)
    }

    def "test getTemplateContentLastValue" () {
        given:
        TemplateTodolistContent content1 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("content1")
                .value(2000)
                .build()
        templateContentRepository.save(content1)

        TemplateTodolistContent content2 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("content2")
                .value(1000)
                .build()
        templateContentRepository.save(content2)

        when:
        int value = customTemplateContentRepository
                .getTemplateContentLastValue(subject.getTemplateTodolistSubjectId())

        then:
        value == 2000
    }

    def "test getTemplateContentValueByOrder" () {
        given:
        TemplateTodolistContent content1 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("content1")
                .value(1000)
                .build()
        content1 = templateContentRepository.save(content1)

        TemplateTodolistContent content2 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("content2")
                .value(2000)
                .build()
        content2 = templateContentRepository.save(content2)

        TemplateTodolistContent content3 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("content2")
                .value(3000)
                .build()
        content3 = templateContentRepository.save(content3)

        when:
        List<Integer> values = customTemplateContentRepository
                .getTemplateContentValueByOrder(subject.getTemplateTodolistSubjectId(),
                        content3.getTemplateTodolistContentId(), 1)

        then:
        values.size() == 2
        values.get(0) == content1.getValue()
        values.get(1) == content2.getValue()
    }

}