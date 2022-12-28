package ru.yandex.practicum.ShareIt.booking;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Booking {
    @NotBlank
    private Long id;
    @NotBlank
    private Long itemId;
    @NotBlank
    private Long userId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean isConfirm;
}
