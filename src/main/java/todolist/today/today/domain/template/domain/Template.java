package todolist.today.today.domain.template.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.global.domain.BaseCreatedAt;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity @Table(name = "TEMPLATE")
public class Template extends BaseCreatedAt {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID templateId;

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    private int size;

    @Column(length = 9, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String profile;

    @Builder
    public Template(User user, int size, String title, String profile) {
        this.user = user;
        this.size = size;
        this.title = title;
        this.profile = profile;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    @OneToMany(mappedBy = "template", cascade = CascadeType.REMOVE)
    private List<TemplateDay> templateDays;

}
