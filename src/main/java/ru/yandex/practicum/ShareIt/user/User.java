package ru.yandex.practicum.ShareIt.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",schema = "PUBLIC")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    @Transient
    private final List<Long> items = new ArrayList<>();
}
