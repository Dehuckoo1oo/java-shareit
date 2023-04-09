package ru.practicum.shareit.requests.DTO;

import lombok.*;
import ru.practicum.shareit.groups.Create;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RequestDTO {
    private Long id;
    private LocalDateTime created;
    @NotBlank(groups = {Create.class})
    private String description;
}
