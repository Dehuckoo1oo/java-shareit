package ru.yandex.practicum.ShareIt.user;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private final List<Long> items = new ArrayList<>();
}
