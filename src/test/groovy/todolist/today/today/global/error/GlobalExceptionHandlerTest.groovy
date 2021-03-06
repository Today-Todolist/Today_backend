package todolist.today.today.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.RequestPostProcessor
import spock.lang.Specification
import todolist.today.today.global.config.ValidatedConfig
import todolist.today.today.global.error.ingredient.TestController
import todolist.today.today.global.error.ingredient.TestDto
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TestController)
@Import(ValidatedConfig)
class GlobalExceptionHandlerTest extends Specification {

    @Autowired
    MockMvc mvc

    @MockBean
    JwtTokenProvider jwtTokenProvider

    @Autowired
    ObjectMapper objectMapper

    def "test handleException" () {
        given:
        TestDto request = new TestDto("test")

        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("test", "test")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().isInternalServerError())
        checkBasicErrorResponse(result, ErrorCode.INTERNAL_SERVER_ERROR)
    }

    def "test handleAccessDeniedException" () {
        when:
        ResultActions result = mvc.perform(get("/auth")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then:
        result.andExpect(status().isUnauthorized())
        checkBasicErrorResponse(result, ErrorCode.UNAUTHORIZED_REQUEST)
    }

    def "test handleMethodArgumentNotValidException" () {
        given:
        TestDto request = new TestDto(null)

        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("test", "test")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().isBadRequest())
        checkBasicErrorResponse(result, ErrorCode.MISSING_REQUEST)
        result.andExpect(jsonPath("reasons").isMap())
    }

    def "test handleConstraintViolationException" () {
        given:
        TestDto request = new TestDto("test")

        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("test", "-1")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().isBadRequest())
        checkBasicErrorResponse(result, ErrorCode.MISSING_REQUEST)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleMissingServletRequestParameterException" () {
        given:
        TestDto request = new TestDto("test")

        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().isBadRequest())
        checkBasicErrorResponse(result, ErrorCode.MISSING_REQUEST)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleMissingServletRequestPartException" () {
        given:
        MockMultipartFile file = new MockMultipartFile("profile", "image.png", "image/png", "".getBytes())

        when:
        ResultActions result = mvc.perform(multipart("/multipart-exception")
                .file(file)
                .with(new RequestPostProcessor() {
                    @Override
                    MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        request.setMethod("POST")
                        return request
                    }
                })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())

        then:
        result.andExpect(status().isBadRequest())
        checkBasicErrorResponse(result, ErrorCode.MISSING_REQUEST)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleHttpMediaTypeNotSupportedException" () {
        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_XML))
                .andDo(print())

        then:
        result.andExpect(status().isUnsupportedMediaType())
        checkBasicErrorResponse(result, ErrorCode.NOT_IN_JSON_FORMAT)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleHttpMessageNotReadableException" () {
        when:
        ResultActions result = mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then:
        result.andExpect(status().isUnsupportedMediaType())
        checkBasicErrorResponse(result, ErrorCode.WRONG_JSON_FORMAT)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleHttpRequestMethodNotSupportedException" () {
        when:
        ResultActions result = mvc.perform(get("/exception"))
                .andDo(print())

        then:
        result.andExpect(status().isMethodNotAllowed())
        checkBasicErrorResponse(result, ErrorCode.WRONG_HTTP_METHOD)
        result.andExpect(jsonPath("reason").isString())
    }

    def "test handleBasicException" () {
        when:
        ResultActions result = mvc.perform(get("/basic"))
                .andDo(print())

        then:
        result.andExpect(status().isInternalServerError())
        checkBasicErrorResponse(result, ErrorCode.INTERNAL_SERVER_ERROR)
    }

    def "test handleInvoluteException" () {
        when:
        ResultActions result = mvc.perform(get("/involute"))
                .andDo(print())

        then:
        result.andExpect(status().isInternalServerError())
        checkBasicErrorResponse(result, ErrorCode.INTERNAL_SERVER_ERROR)
        result.andExpect(jsonPath("reasons").isMap())
    }

    def "test handleSimpleException" () {
        when:
        ResultActions result = mvc.perform(get("/simple"))
                .andDo(print())

        then:
        result.andExpect(status().isInternalServerError())
        checkBasicErrorResponse(result, ErrorCode.INTERNAL_SERVER_ERROR)
        result.andExpect(jsonPath("reason").isString())
    }

    def checkBasicErrorResponse (ResultActions result, ErrorCode errorCode) {
        return result.andExpect(jsonPath("status").value(errorCode.getStatus())) ||
                result.andExpect(jsonPath("code").value(errorCode.getCode())) ||
                result.andExpect(jsonPath("message").value(errorCode.getMessage()))
    }

}
