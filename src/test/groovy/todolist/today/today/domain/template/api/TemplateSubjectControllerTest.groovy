package todolist.today.today.domain.template.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateDayRepository
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.dao.TemplateSubjectRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.domain.TemplateDay
import todolist.today.today.domain.template.domain.TemplateTodolistSubject
import todolist.today.today.domain.template.dto.request.TemplateSubjectChangeRequest
import todolist.today.today.domain.template.dto.request.TemplateSubjectCreateRequest
import todolist.today.today.domain.template.dto.request.TemplateSubjectOrderRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class TemplateSubjectControllerTest extends Specification {

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
    JwtTokenProvider jwtTokenProvider

    @Autowired
    ObjectMapper objectMapper

    User user
    Template template
    TemplateDay templateDay

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
        templateDayRepository.save(templateDay)
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test makeTemplateSubject" () {
        given:
        final String TEMPLATE_ID = requestIdExists ? template.getTemplateId().toString() : UUID.randomUUID().toString()
        TemplateSubjectCreateRequest request =
                makeTemplateSubjectCreateRequest(TEMPLATE_ID, 1, "계획하자!")
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(post("/template/subject")
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

    def "test changeTemplateSubject" () {
        given:
        TemplateTodolistSubject subject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("")
                .value(100)
                .build()
        subject = templateSubjectRepository.save(subject)

        final String requestSubjectId = requestIdExists ? subject.getTemplateTodolistSubjectId().toString() : UUID.randomUUID().toString()

        TemplateSubjectChangeRequest request = makeTemplateSubjectChangeRequest("oh! today")
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(put("/template/subject/{subjectId}", requestSubjectId)
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

    def "test changeTemplateSubjectOrder" () {
        given:
        TemplateTodolistSubject subject1 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("")
                .value(100)
                .build()
        subject1 = templateSubjectRepository.save(subject1)

        TemplateTodolistSubject subject2 = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("")
                .value(200)
                .build()
        templateSubjectRepository.save(subject2)

        final String requestSubjectId = requestIdExists ? subject1.getTemplateTodolistSubjectId().toString() : UUID.randomUUID().toString()

        TemplateSubjectOrderRequest request = makeTemplateSubjectOrderRequest(0)
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(put("/template/subject-order/{subjectId}", requestSubjectId)
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

    def "test deleteTemplateSubject" () {
        given:
        TemplateTodolistSubject subject = TemplateTodolistSubject.builder()
                .templateDay(templateDay)
                .subject("")
                .value(100)
                .build()
        subject = templateSubjectRepository.save(subject)

        final String requestSubjectId = requestIdExists ? subject.getTemplateTodolistSubjectId().toString() : UUID.randomUUID().toString()
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(delete("/template//subject/{subjectId}", requestSubjectId)
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