package todolist.today.today.domain.template.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import todolist.today.today.global.domain.BaseTodolistContent;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class TemplateTodolistContent extends BaseTodolistContent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID templateTodolistContentId;

    @ManyToOne
    @JoinColumn(name = "template_todolist_subject_id", nullable = false)
    private TemplateTodolistSubject templateTodolistSubject;

    @Builder
    public TemplateTodolistContent(TemplateTodolistSubject templateTodolistSubject, String content, int value) {
        this.templateTodolistSubject = templateTodolistSubject;
        this.content = content;
        this.value = value;
    }

}