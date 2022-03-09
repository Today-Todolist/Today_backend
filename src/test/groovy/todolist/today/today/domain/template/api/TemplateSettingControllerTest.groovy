package todolist.today.today.domain.template.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.RequestPostProcessor
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.dto.request.TemplateCreateRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider
import todolist.today.today.infra.file.image.ImageUploadFacade

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.makeTemplateCreateRequest

@SpringBootTest
@AutoConfigureMockMvc
class TemplateSettingControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private ObjectMapper objectMapper

    @MockBean
    private ImageUploadFacade imageUploadFacade

    private static final String EMAIL = "today043149@gmail.com"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test makeTemplate" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)
        given(imageUploadFacade.uploadRandomImage()).willReturn("profile")

        String token = jwtTokenProvider.generateAccessToken(EMAIL)
        TemplateCreateRequest request = makeTemplateCreateRequest("today", 3)

        when:
        ResultActions result = mvc.perform(post("/template")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeTemplateProfile" () {
        given:
        User user = User.builder()
                .email(EMAIL)
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

        MockMultipartFile image = new MockMultipartFile("profile", "image.png", "image/png", "".getBytes())
        given(imageUploadFacade.uploadImage(any())).willReturn("profile")

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(multipart("/template-profile/{templateId}", template.getTemplateId())
                .file(image)
                .with(new RequestPostProcessor() {
                    @Override
                    MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        request.setMethod("PUT")
                        return request
                    }
                })
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test deleteTemplate" () {
        given:
        User user = User.builder()
                .email(EMAIL)
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

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(delete("/template/{templateId}", template.getTemplateId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(204))
    }

}