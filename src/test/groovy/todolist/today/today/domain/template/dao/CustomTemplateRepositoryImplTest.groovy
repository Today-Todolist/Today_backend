package todolist.today.today.domain.template.dao


import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.search.dto.response.TemplateSearchResponse
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.template.dto.response.MyTemplateResponse
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse
import todolist.today.today.domain.template.dto.response.TemplateContentResponse
import todolist.today.today.domain.todolist.dto.etc.TemplateContentDto
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig
import todolist.today.today.global.dto.request.PagingRequest

import javax.persistence.EntityManager

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomTemplateRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomTemplateRepositoryImpl customTemplateRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private TemplateDayRepository templateDayRepository

    @Autowired
    private TemplateSubjectRepository templateSubjectRepository

    private User user

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customTemplateRepository = new CustomTemplateRepositoryImpl(new JPAQueryFactory(em))
        user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("userProfile")
                .build()
        user = userRepository.save(user)
    }

    def "test getTemplateSearchResult" () {
        given:
        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("title")
                .profile("templateProfile")
                .build()
        templateRepository.save(template)

        when:
        List<TemplateSearchResponse> response = customTemplateRepository.getTemplateSearchResult("itl", new PagingRequest(0, 1))

        then:
        response.size() == 1
        response.get(0).getUser().getEmail() == user.getEmail()
        response.get(0).getUser().getNickname() == user.getNickname()
        response.get(0).getUser().getProfile() == user.getProfile()
        response.get(0).getTemplate().getId() == template.getTemplateId().toString()
        response.get(0).getTemplate().getTitle() == template.getTitle()
        response.get(0).getTemplate().getProfile() == template.getProfile()
    }

    def "test getRandomTemplate" () {
        given:
        Template template1 = Template.builder()
                .user(user)
                .size(7)
                .title("title1")
                .profile("templateProfile1")
                .build()
        templateRepository.save(template1)

        Template template2 = Template.builder()
                .user(user)
                .size(7)
                .title("title2")
                .profile("templateProfile2")
                .build()
        templateRepository.save(template2)

        when:
        List<RandomTemplateResponse> response = customTemplateRepository.getRandomTemplate(1, 2)

        then:
        response.size() == 1
    }

    def "test getMyTemplate" () {
        given:
        Template template1 = Template.builder()
                .user(user)
                .size(7)
                .title("title1")
                .profile("templateProfile1")
                .build()
        templateRepository.save(template1)

        Template template2 = Template.builder()
                .user(user)
                .size(7)
                .title("title2")
                .profile("templateProfile2")
                .build()
        templateRepository.save(template2)

        when:
        List< MyTemplateResponse> reponse = customTemplateRepository.getMyTemplate(user.getEmail())

        then:
        reponse.size() == 2
    }

    def "test getTemplateContent" () {
        given:
        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("title")
                .profile("templateProfile")
                .build()
        template = templateRepository.save(template)

        TemplateDay templateDay = TemplateDay.builder()
                .template(template)
                .day(1)
                .build()
        templateDay = templateDayRepository.save(templateDay)

        TemplateTodolistSubject templateTodolistSubject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("")
                .value(100)
                .build()
        templateSubjectRepository.save(templateTodolistSubject)

        when:
        TemplateContentResponse response = customTemplateRepository
                .getTemplateContent(user.getEmail(), template.getTemplateId(), 1)

        then:
        response.getTitle() == template.getTitle()
        response.getProfile() == template.getProfile()
        response.getLength() == template.size
        response.getStatus() == 1
    }

    def "test getTemplateProfile" () {
        given:
        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("title")
                .profile("templateProfile")
                .build()
        templateRepository.save(template)

        when:
        String profile = customTemplateRepository.getTemplateProfile(user.getEmail(), template.getTemplateId())

        then:
        profile == template.getProfile()
    }

    def "test getUserTemplateInfo" () {
        given:
        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("title")
                .profile("templateProfile")
                .build()
        templateRepository.save(template)

        when:
        List<TemplateContentDto> response = customTemplateRepository.getUserTemplateInfo(user.getEmail(), template.getTemplateId())

        then:
        response.size() == 0
    }

}