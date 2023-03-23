package ru.yandex.practicum.ShareIt.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ShareIt.requests.Request;
import ru.yandex.practicum.ShareIt.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
    List<Booking> findAllByItem_Id(Long itemId);

    Page<Booking> findAllByBooker(User user, Pageable pageable);

    Page<Booking> findAllByBooker_IdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByBooker_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByBooker_IdAndStartIsAfter(Long bookerId, LocalDateTime start, Pageable pageable);

    Page<Booking> findAllByBooker_IdAndStatus(Long bookerId, Status status, Pageable pageable);

    Page<Booking> findAllByItem_Owner_IdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByItem_Owner_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByItem_Owner_IdAndStartIsAfter(Long bookerId, LocalDateTime start, Pageable pageable);

    Page<Booking> findAllByItem_Owner_IdAndStatus(Long bookerId, Status status, Pageable pageable);
    Page<Booking> findAllByItem_Owner_Id(Long ownerId, Pageable pageable);
}
