package todolist.today.today.domain.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import todolist.today.today.global.domain.BaseTodolistSubject;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class TodolistSubject extends BaseTodolistSubject {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID todolistSubjectId;

    @ManyToOne
    @JoinColumn(name = "todolist_id", nullable = false)
    private Todolist todolist;

    @Builder
    public TodolistSubject(Todolist todolist, String subject, int value) {
        this.todolist = todolist;
        this.subject = subject;
        this.value = value;
    }

    @OneToMany(mappedBy = "todolistSubject", cascade = CascadeType.REMOVE)
    private List<TodolistContent> todolistContents;

}
