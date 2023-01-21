package ru.yandex.practicum.ShareIt.item;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items",schema = "PUBLIC")
public class Item {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long owner;
    private Boolean available;
}
