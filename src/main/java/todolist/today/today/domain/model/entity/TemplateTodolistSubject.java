package todolist.today.today.domain.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import todolist.today.today.global.jpa.entity.BaseTodolistSubject;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity @Table(name = "TEMPLATE_TODOLIST_SUBJECT")
public class TemplateTodolistSubject extends BaseTodolistSubject {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID templateTodolistSubjectId;

    @ManyToOne
    @JoinColumn(name = "template_day_id", nullable = false)
    private TemplateDay templateDay;

    @Builder
    public TemplateTodolistSubject(TemplateDay templateDay, String subject, int value) {
        this.templateDay = templateDay;
        this.subject = subject;
        this.value = value;
    }

    @OneToMany(mappedBy = "templateTodolistSubject", cascade = CascadeType.REMOVE)
    private List<TemplateTodolistContent> templateTodolistContents;

}