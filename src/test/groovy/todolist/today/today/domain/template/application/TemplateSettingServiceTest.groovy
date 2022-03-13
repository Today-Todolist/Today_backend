package todolist.today.today.domain.template.application


import spock.lang.Specification
import todolist.today.today.domain.check.application.CheckService
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.template.dto.request.TemplateCreateRequest
import todolist.today.today.domain.template.exception.TemplateNotFoundException
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.exception.UserNotFoundException
import todolist.today.today.infra.file.image.ImageUploadFacade

import static todolist.today.today.RequestUtil.makeTemplateCreateRequest

class TemplateSettingServiceTest extends Specification {

    private TemplateSettingService templateSettingService
    private CustomTemplateRepositoryImpl customTemplateRepository = Stub(CustomTemplateRepositoryImpl)
    private TemplateRepository templateRepository = Stub(TemplateRepository)
    private UserRepository userRepository = Stub(UserRepository)
    private ImageUploadFacade imageUploadFacade = Stub(ImageUploadFacade)
    private CheckService checkService = Stub(CheckService)

    def setup() {
        templateSettingService = new TemplateSettingService(
                customTemplateRepository,
                templateRepository,
                userRepository,
                imageUploadFacade,
                checkService)
    }

    def "test makeTemplate" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        TemplateCreateRequest request = makeTemplateCreateRequest("", 1)
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        templateSettingService.makeTemplate(USER_ID, request)

        then:
        noExceptionThrown()
    }

    def "test makeTemplate UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        TemplateCreateRequest request = makeTemplateCreateRequest("", 1)
        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        templateSettingService.makeTemplate(USER_ID, request)

        then:
        thrown(UserNotFoundException)
    }

    def "test changeTemplateProfile" () {
        given:
        final UUID TEMPLATE_ID = UUID.randomUUID()
        final String USER_ID = "today043149@gmail.com"
        User user = User.builder()
                .email(USER_ID)
                .nickname("")
                .profile("")
                .build()

        Template template = Template.builder()
                .user(user)
                .size(1)
                .title("")
                .profile("")
                .build()
        templateRepository.findById(TEMPLATE_ID) >> Optional.of(template)

        when:
        templateSettingService.changeTemplateProfile(USER_ID, TEMPLATE_ID.toString(), null)

        then:
        noExceptionThrown()
    }

    def "test changeTemplateProfile TemplateNotFoundException" () {
        given:
        final UUID TEMPLATE_ID = UUID.randomUUID()
        templateRepository.findById(TEMPLATE_ID) >> Optional.empty()

        when:
        templateSettingService.changeTemplateProfile("", TEMPLATE_ID.toString(), null)

        then:
        thrown(TemplateNotFoundException)
    }

    def "test deleteTemplate" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TEMPLATE_ID = UUID.randomUUID()
        customTemplateRepository.getTemplateProfile(USER_ID, TEMPLATE_ID) >> ""

        when:
        templateSettingService.deleteTemplate(USER_ID, TEMPLATE_ID.toString())

        then:
        noExceptionThrown()
    }

    def "test deleteTemplate TemplateNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TEMPLATE_ID = UUID.randomUUID()
        customTemplateRepository.getTemplateProfile(USER_ID, TEMPLATE_ID) >> null

        when:
        templateSettingService.deleteTemplate(USER_ID, TEMPLATE_ID.toString())

        then:
        thrown(TemplateNotFoundException)
    }

}