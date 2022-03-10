package todolist.today.today.domain.template.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateDayRepository
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TemplateInfoControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private TemplateDayRepository templateDayRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private ObjectMapper objectMapper

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test getRandomTemplate" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        templateRepository.save(template)

        when:
        ResultActions result = mvc.perform(get("/random-template")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getMyTemplate" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        templateRepository.save(template)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/templates")
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getTemplateContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        template = templateRepository.save(template)

        TemplateDay templateDay = TemplateDay.builder()
                .template(template)
                .day(1)
                .build()
        templateDayRepository.save(templateDay)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/template/{templateId}", template.getTemplateId())
                .header("Authorization", "Bearer " + token)
                .param("day", day.toString()))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        day || status
        1 || 200
        2 || 404
    }

}