package ru.yandex.practicum.ShareIt.requests.DTO;

import lombok.*;
import ru.yandex.practicum.ShareIt.groups.Create;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class RequestDTO {
    private Long id;
    private LocalDateTime created;
    @NotBlank(groups = {Create.class})
    private String description;
}
