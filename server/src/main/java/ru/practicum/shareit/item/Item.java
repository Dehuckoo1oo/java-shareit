package ru.practicum.shareit.item;

import javax.persistence.*;
import lombok.*;
import ru.practicum.shareit.requests.Request;
import ru.practicum.shareit.user.User;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items", schema = "PUBLIC")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private User owner;
    private Boolean available;
    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private Request request;
}


