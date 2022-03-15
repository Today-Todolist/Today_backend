package todolist.today.today.domain.template.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateContentRepository
import todolist.today.today.domain.template.dao.TemplateDayRepository
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistContent
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.template.dto.request.TemplateContentChangeRequest
import todolist.today.today.domain.template.dto.request.TemplateContentCreateRequest
import todolist.today.today.domain.template.dto.request.TemplateContentOrderRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class TemplateContentControllerTest extends Specification {

    @Autowired
    MockMvc mvc

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

    @Autowired
    JwtTokenProvider jwtTokenProvider

    @Autowired
    ObjectMapper objectMapper

    User user
    Template template
    TemplateDay templateDay
    TemplateTodolistSubject subject

    def setup() {
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
                .subject("")
                .value(100)
                .build()
        templateSubjectRepository.save(subject)
    }

    def "test makeTemplateContent" () {
        given:
        final String SUBJECT_ID = requestIdExists ? subject.getTemplateTodolistSubjectId().toString() : UUID.randomUUID().toString()
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TemplateContentCreateRequest request = makeTemplateContentCreateRequest(SUBJECT_ID, "beautiful today!")

        when:
        ResultActions result = mvc.perform(post("/template/content")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestIdExists || status
        true || 201
        false || 404
    }

    def "test changeTemplateContent" () {
        given:
        TemplateTodolistContent content = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("")
                .value(100)
                .build()
        templateContentRepository.save(content)

        final String CONTENT_ID = requestIdExists ? content.getTemplateTodolistContentId().toString() : UUID.randomUUID().toString()

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TemplateContentChangeRequest request = makeTemplateContentChangeRequest("test very hard...ㅠ")

        when:
        ResultActions result = mvc.perform(put("/template/content/{contentId}", CONTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestIdExists || status
        true || 201
        false || 404
    }

    def "test changeTemplateContentOrder" () {
        given:
        TemplateTodolistContent content1 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("")
                .value(100)
                .build()
        templateContentRepository.save(content1)

        TemplateTodolistContent content2 = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("")
                .value(200)
                .build()
        templateContentRepository.save(content2)

        final String CONTENT_ID = requestIdExists ? content1.getTemplateTodolistContentId().toString() : UUID.randomUUID().toString()

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TemplateContentOrderRequest request = makeTemplateContentOrderRequest(0)

        when:
        ResultActions result = mvc.perform(put("/template/content-order/{contentId}", CONTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestIdExists || status
        true || 201
        false || 404
    }

    def "test deleteTemplateContent" () {
        given:
        TemplateTodolistContent content = TemplateTodolistContent.builder()
                .templateTodolistSubject(subject)
                .content("")
                .value(100)
                .build()
        templateContentRepository.save(content)

        final String CONTENT_ID = requestIdExists ? content.getTemplateTodolistContentId().toString() : UUID.randomUUID().toString()

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(delete("/template/content/{contentId}", CONTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestIdExists || status
        true || 204
        false || 404
    }

}