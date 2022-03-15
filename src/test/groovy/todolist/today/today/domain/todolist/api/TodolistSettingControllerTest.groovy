package todolist.today.today.domain.todolist.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.dto.request.TemplateApplyRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.makeTemplateApplyRequest
import static todolist.today.today.global.dto.LocalDateUtil.convert

@SpringBootTest
@AutoConfigureMockMvc
class TodolistSettingControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    @Autowired
    private TodolistSubjectRepository todolistSubjectRepository

    @Autowired
    private TodolistContentRepository todolistContentRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private ObjectMapper objectMapper

    private User user
    private Template template

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
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test applyTemplate" () {
        given:
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TemplateApplyRequest request = makeTemplateApplyRequest(Arrays.asList(template.getTemplateId().toString()))

        when:
        ResultActions result = mvc.perform(post("/apply-template")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .param("date", convert(LocalDate.now()))
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

}