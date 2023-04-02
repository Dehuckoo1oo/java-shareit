package ru.yandex.practicum.ShareIt.item.comments;

import lombok.*;
import ru.yandex.practicum.ShareIt.groups.Create;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String text;
    private LocalDateTime created;
    private Long itemId;
    private String authorName;
}
