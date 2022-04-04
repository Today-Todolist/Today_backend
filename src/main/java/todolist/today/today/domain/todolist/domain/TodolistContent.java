package todolist.today.today.domain.todolist.domain;

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
public class TodolistContent extends BaseTodolistContent {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID todolistContentId;

    @ManyToOne
    @JoinColumn(name = "todolist_subject_id", nullable = false)
    private TodolistSubject todolistSubject;

    private boolean isSuccess;

    @Builder
    public TodolistContent(TodolistSubject todolistSubject, String content, int value) {
        this.todolistSubject = todolistSubject;
        this.content = content;
        this.value = value;
        this.isSuccess = false;
    }

    public void updateIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
