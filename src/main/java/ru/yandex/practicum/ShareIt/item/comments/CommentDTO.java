package ru.yandex.practicum.ShareIt.item.comments;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.ShareIt.groups.Create;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String text;
    private Long itemId;
    private String authorName;
}
