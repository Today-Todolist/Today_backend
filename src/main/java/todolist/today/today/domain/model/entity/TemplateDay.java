package todolist.today.today.domain.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity @Table(name = "TEMPLATE_DAY")
public class TemplateDay {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID templateDayId;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    private int day;

    @Builder
    public TemplateDay(Template template, int day) {
        this.template = template;
        this.day = day;
    }

    @OneToMany(mappedBy = "templateDay", cascade = CascadeType.REMOVE)
    private List<TemplateTodolistSubject> templateTodolistSubjects;

}
