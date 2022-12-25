package ru.yandex.practicum.ShareIt.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class User {

    interface New {

    }

    interface Exist {

    }

    interface Update extends Exist{

    }

    @Null(groups = {New.class})
    private Long id;
    @NotBlank(groups = {New.class})
    private String name;
    @NotBlank(groups = {New.class})
    @Email(groups = {New.class})
    private String email;
    private final List<Long> items = new ArrayList<>();
}
