package todolist.today.today.domain.template.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.friend.domain.Friend
import todolist.today.today.domain.search.dto.response.TemplateSearchResponse
import todolist.today.today.domain.template.domain.Template
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

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customTemplateRepository = new CustomTemplateRepositoryImpl(new JPAQueryFactory(em))
    }

    def "test getTemplateSearchResult" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("userProfile")
                .build()
        user = userRepository.save(user)

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

}