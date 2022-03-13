package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.dto.response.TemplateContentResponse


class TemplateInfoServiceTest extends Specification {

    private TemplateInfoService templateInfoService
    private CustomTemplateRepositoryImpl customTemplateRepository = Stub(CustomTemplateRepositoryImpl)
    private TemplateRepository templateRepository = Stub(TemplateRepository)

    def setup() {
        templateInfoService = new TemplateInfoService(customTemplateRepository, templateRepository)
    }

    def "test getRandomTemplate" () {
        given:
        final int SIZE = 1
        templateRepository.count() >> count
        customTemplateRepository.getRandomTemplate(SIZE, 1L) >> Collections.emptyList()

        when:
        templateInfoService.getRandomTemplate(SIZE)

        then:
        noExceptionThrown()

        where:
        count << [0L, 1L]
    }

    def "test getMyTemplate" () {
        when:
        templateInfoService.getMyTemplate("")

        then:
        noExceptionThrown()
    }

    def "test getTemplateContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TEMPLATE_ID = UUID.randomUUID()
        final int DAY = 1
        customTemplateRepository.getTemplateContent(USER_ID, TEMPLATE_ID, DAY)
                >> Stub(TemplateContentResponse)

        when:
        templateInfoService.getTemplateContent(USER_ID, TEMPLATE_ID.toString(), DAY)

        then:
        noExceptionThrown()
    }

}