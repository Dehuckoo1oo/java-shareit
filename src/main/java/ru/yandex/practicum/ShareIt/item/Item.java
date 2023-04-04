package ru.yandex.practicum.ShareIt.item;

import lombok.*;
import ru.yandex.practicum.ShareIt.requests.Request;
import ru.yandex.practicum.ShareIt.user.User;

import javax.persistence.*;

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


