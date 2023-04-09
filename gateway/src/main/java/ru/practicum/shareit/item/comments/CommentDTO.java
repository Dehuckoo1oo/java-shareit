package ru.practicum.shareit.item.comments;

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
public class CommentDTO {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String text;
    private LocalDateTime created;
    private Long itemId;
    private String authorName;
}
