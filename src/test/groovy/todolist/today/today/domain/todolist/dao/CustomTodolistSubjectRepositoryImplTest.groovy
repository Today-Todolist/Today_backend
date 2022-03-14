package todolist.today.today.domain.todolist.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig

import javax.persistence.EntityManager
import java.time.LocalDate

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomTodolistSubjectRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomTodolistSubjectRepositoryImpl customTodolistSubjectRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    @Autowired
    private TodolistSubjectRepository todolistSubjectRepository

    private User user
    private Todolist todolist

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customTodolistSubjectRepository = new CustomTodolistSubjectRepositoryImpl(new JPAQueryFactory(em))

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
    }

    def "test getTodolistSubjectLastValue" () {
        given:
        TodolistSubject subject1 = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(2000)
                .build()
        todolistSubjectRepository.save(subject1)

        TodolistSubject subject2 = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(1000)
                .build()
        todolistSubjectRepository.save(subject2)

        when:
        int value = customTodolistSubjectRepository.getTodolistSubjectLastValue(todolist.getTodolistId())

        then:
        value == 2000
    }

    def "test getTodolistSubjectValueByOrder" () {
        given:
        TodolistSubject subject1 = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(1000)
                .build()
        todolistSubjectRepository.save(subject1)

        TodolistSubject subject2 = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(2000)
                .build()
        todolistSubjectRepository.save(subject2)

        TodolistSubject subject3 = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(3000)
                .build()
        todolistSubjectRepository.save(subject3)

        when:
        List<Integer> values = customTodolistSubjectRepository
                .getTodolistSubjectValueByOrder(todolist.getTodolistId(),
                        subject3.getTodolistSubjectId(), 1)

        then:
        values.size() == 2
        values.get(0) == subject1.value
        values.get(1) == subject2.value
    }

}