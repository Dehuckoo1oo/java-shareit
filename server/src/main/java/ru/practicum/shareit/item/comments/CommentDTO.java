package ru.practicum.shareit.item.comments;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime created;
    private Long itemId;
    private String authorName;
}
