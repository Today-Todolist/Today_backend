package todolist.today.today.domain.template.application

import spock.lang.Specification
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl
import todolist.today.today.domain.template.dao.TemplateRepository


class TemplateInfoServiceTest extends Specification {

    private TemplateInfoService templateInfoService
    private CustomTemplateRepositoryImpl customTemplateRepository = Stub(CustomTemplateRepositoryImpl)
    private TemplateRepository templateRepository = Stub(TemplateRepository)

    def setup() {
        templateInfoService = new TemplateInfoService(customTemplateRepository, templateRepository)
    }

    def "test getRandomTemplate" () {
        given:
        final int SIZE = 1;
        templateRepository.count() >> 1L
        customTemplateRepository.getRandomTemplate(SIZE, 1L) >> Collections.emptyList()

        when:
        templateInfoService.getRandomTemplate(SIZE)

        then:
        noExceptionThrown()
    }

    def "test getMyTemplate" () {
        when:
        templateInfoService.getMyTemplate("")

        then:
        noExceptionThrown()
    }

    def "test getTemplateContent" () {
        given:
        final UUID TEMPLATE_ID = UUID.randomUUID()
        templateRepository.existsById(TEMPLATE_ID) >> true

        when:
        templateInfoService.getTemplateContent("", TEMPLATE_ID.toString(), 1)

        then:
        noExceptionThrown()
    }

}