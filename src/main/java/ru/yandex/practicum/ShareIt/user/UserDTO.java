package ru.yandex.practicum.ShareIt.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserDTO {

    interface New {

    }

    interface Exist {

    }

    interface Update extends Exist {

    }

    @Null(groups = {New.class})
    private Long id;
    @NotBlank(groups = {New.class})
    private String name;
    @NotBlank(groups = {New.class})
    @Email(groups = {New.class})
    private String email;
}
