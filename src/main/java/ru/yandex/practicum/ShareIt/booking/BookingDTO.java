package ru.yandex.practicum.ShareIt.booking;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class BookingDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private Long itemId;
    @NotBlank
    private Long userId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
