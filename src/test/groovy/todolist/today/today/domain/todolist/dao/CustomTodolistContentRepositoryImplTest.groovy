package todolist.today.today.domain.todolist.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig

import javax.persistence.EntityManager
import java.time.LocalDate

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomTodolistContentRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomTodolistContentRepositoryImpl customTodolistContentRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    @Autowired
    private TodolistSubjectRepository todolistSubjectRepository

    @Autowired
    private TodolistContentRepository todolistContentRepository

    private User user
    private Todolist todolist
    private TodolistSubject subject

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customTodolistContentRepository = new CustomTodolistContentRepositoryImpl(new JPAQueryFactory(em))

        user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()
        todolist = todolistRepository.save(todolist)

        subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()
        subject = todolistSubjectRepository.save(subject)
    }

    def "test getTodolistContentLastValue" () {
        given:
        TodolistContent content1 = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content1")
                .value(2000)
                .build()
        todolistContentRepository.save(content1)

        TodolistContent content2 = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content2")
                .value(1000)
                .build()
        todolistContentRepository.save(content2)

        when:
        int value = customTodolistContentRepository.getTodolistContentLastValue(subject.getTodolistSubjectId())

        then:
        value == 2000
    }

    def "test getTodolistContentValueByOrder" () {
        given:
        TodolistContent content1 = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content1")
                .value(1000)
                .build()
        todolistContentRepository.save(content1)

        TodolistContent content2 = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content2")
                .value(2000)
                .build()
        todolistContentRepository.save(content2)

        TodolistContent content3 = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content3")
                .value(3000)
                .build()
        todolistContentRepository.save(content3)

        when:
        List<Integer> values = customTodolistContentRepository
                .getTodolistContentValueByOrder(subject.getTodolistSubjectId(),
                        content3.getTodolistContentId(), 1)

        then:
        values.size() == 2
        values.get(0) == content1.value
        values.get(1) == content2.value
    }

}