package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.RequestUtil
import todolist.today.today.domain.template.dao.CustomTemplateRepositoryImpl
import todolist.today.today.domain.todolist.dao.*
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.dto.etc.TemplateContentDto
import todolist.today.today.domain.todolist.dto.etc.content.TemplateContentSubjectContentDto
import todolist.today.today.domain.todolist.dto.etc.subject.TemplateContentSubjectDto
import todolist.today.today.domain.todolist.dto.request.TemplateApplyRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User

import java.time.LocalDate

class TodolistSettingServiceTest extends Specification {

    private TodolistSettingService todolistSettingService
    private CustomTemplateRepositoryImpl customTemplateRepository = Stub(CustomTemplateRepositoryImpl)
    private UserRepository userRepository = Stub(UserRepository)
    private TodolistRepository todolistRepository = Stub(TodolistRepository)
    private CustomTodolistSubjectRepositoryImpl customTodolistSubjectRepository = Stub(CustomTodolistSubjectRepositoryImpl)
    private TodolistSubjectRepository todolistSubjectRepository = Stub(TodolistSubjectRepository)
    private CustomTodolistContentRepositoryImpl customTodolistContentRepository = Stub(CustomTodolistContentRepositoryImpl)
    private TodolistContentRepository todolistContentRepository = Stub(TodolistContentRepository)
    private TodolistSortService todolistSortService = Stub(TodolistSortService)

    def setup() {
        todolistSettingService = new TodolistSettingService(customTemplateRepository,
                userRepository,
                todolistRepository,
                customTodolistSubjectRepository,
                todolistSubjectRepository,
                customTodolistContentRepository,
                todolistContentRepository,
                todolistSortService)
    }

    def "test applyTemplate" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TEMPLATE_ID = UUID.randomUUID()

        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.findById(USER_ID) >> Optional.of(user)

        TemplateApplyRequest request = RequestUtil.makeTemplateApplyRequest(Arrays.asList(TEMPLATE_ID.toString()))

        List<TemplateContentSubjectContentDto> content = Arrays.asList(new TemplateContentSubjectContentDto(""))
        List<TemplateContentSubjectDto> subject = Arrays.asList(new TemplateContentSubjectDto("", content))
        List<TemplateContentDto> templateInfo = Arrays.asList(new TemplateContentDto(1, subject))

        customTemplateRepository.getUserTemplateInfo(USER_ID, TEMPLATE_ID) >> templateInfo
        customTodolistSubjectRepository.getTodolistSubjectLastValue(_) >> value
        customTodolistContentRepository.getTodolistContentLastValue(_) >> value

        todolistRepository.save(_) >> new Todolist()
        todolistSubjectRepository.save(_) >> new TodolistSubject()

        when:
        todolistSettingService.applyTemplate(USER_ID, LocalDate.now(), request)

        then:
        noExceptionThrown()

        where:
        value << [1, 2147483500]
    }

}