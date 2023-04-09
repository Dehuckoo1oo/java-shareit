package ru.practicum.shareit.requests.DTO;

import lombok.*;

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
    private String description;
}
