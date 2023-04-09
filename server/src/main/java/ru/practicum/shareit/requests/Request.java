package ru.practicum.shareit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.User;
import javax.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests", schema = "PUBLIC")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "requester", referencedColumnName = "id")
    private User user;
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;
    private String description;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
}
