package todolist.today.today.domain.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import todolist.today.today.domain.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity @Table(name = "TODOLIST")
public class Todolist {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID todolistId;

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Builder
    public Todolist(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }

    @OneToMany(mappedBy = "todolist", cascade = CascadeType.REMOVE)
    private List<TodolistSubject> todolistSubjects;

}
