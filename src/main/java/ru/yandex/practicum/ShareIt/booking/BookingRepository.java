package ru.yandex.practicum.ShareIt.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByItem_Id(Long itemId);

    List<Booking> findAllByBooker_Id(Long bookerId, Sort by);

    List<Booking> findAllByBooker_IdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort by);

    List<Booking> findAllByItem_Owner_Id(Long ownerId, Sort by);

    List<Booking> findAllByBooker_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Sort by);

    List<Booking> findAllByBooker_IdAndStartIsAfter(Long bookerId, LocalDateTime start, Sort by);

    List<Booking> findAllByBooker_IdAndStatus(Long bookerId, Status status, Sort by);

    List<Booking> findAllByItem_Owner_IdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort by);

    List<Booking> findAllByItem_Owner_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Sort by);

    List<Booking> findAllByItem_Owner_IdAndStartIsAfter(Long bookerId, LocalDateTime start, Sort by);

    List<Booking> findAllByItem_Owner_IdAndStatus(Long bookerId, Status status, Sort by);

}
