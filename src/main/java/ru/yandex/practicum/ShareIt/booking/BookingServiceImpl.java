package ru.yandex.practicum.ShareIt.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTORequest;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingDTOResponse;
import ru.yandex.practicum.ShareIt.booking.DTO.BookingMapper;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;
import ru.yandex.practicum.ShareIt.exception.NotFoundResourceException;
import ru.yandex.practicum.ShareIt.exception.UnsupportedStatusException;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.item.ItemService;
import ru.yandex.practicum.ShareIt.user.User;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    UserService userService;
    ItemService itemService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public BookingDTOResponse create(BookingDTORequest bookingDTORequest, Long userId) {
        User booker = userService.getUserById(userId);
        Item item = itemService.getItemById(bookingDTORequest.getItemId());
        if (item.getOwner().getId().equals(booker.getId())) {
            throw new NoSuchBodyException("Нельзя бронировать свой предмет");
        }
        if (!item.getAvailable()) {
            throw new NotFoundResourceException(String.format("Предмест с id %s не доступен для бронирования",
                    item.getId()));
        }
        if (bookingDTORequest.getEnd().isBefore(bookingDTORequest.getStart())) {
            throw new NotFoundResourceException("Дата старта бронирования позже даты окончания");
        }
        Booking booking = new Booking(null, bookingDTORequest.getStart(), bookingDTORequest.getEnd(), item, booker,
                Status.WAITING);
        Booking resultBooking = bookingRepository.save(booking);
        return BookingMapper.mapEntityToDTO(resultBooking);
    }

    @Override
    public BookingDTOResponse updateStatus(Long userId, Long bookingId, Boolean approved) {
        Booking booking = findBookingById(userId, bookingId);
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new UnsupportedStatusException("Бронирование уже подтверждено");
        }
        if (booking.getItem().getOwner().getId().equals(userId)) {
            if (approved) {
                booking.setStatus(Status.APPROVED);
            } else {
                booking.setStatus(Status.REJECTED);
            }
        } else {
            throw new NoSuchBodyException(String.format("Пользователь с id %s не является владельцем предмета", userId));
        }
        bookingRepository.save(booking);
        return BookingMapper.mapEntityToDTO(booking);
    }

    @Override
    public BookingDTOResponse getBookingById(Long userId, Long bookingId) {
        return BookingMapper.mapEntityToDTO(findBookingById(userId, bookingId));
    }

    private Booking findBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> {
            throw new NoSuchBodyException(String.format("Бронирование с id %s отсутствует", bookingId));
        });
        if (booking.getItem().getOwner().getId().equals(userId) || booking.getBooker().getId().equals(userId)) {
            return booking;
        } else {
            throw new NoSuchBodyException(String.format("У пользователя с id %s отсутствует доступ к данному бронированию",
                    userId));
        }
    }

    @Override
    public List<BookingDTOResponse> getBookingByCurrentUser(Long userId, String strState) {
        State state = checkStatus(strState);

        userService.getUserById(userId);
        Sort sortBy = Sort.by(Sort.Direction.DESC, "start");
        if (state.equals(State.ALL)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_Id(userId, sortBy));
        } else if (state.equals(State.CURRENT)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_IdAndStartIsBeforeAndEndIsAfter(userId,
                    LocalDateTime.now(), LocalDateTime.now(), sortBy));
        } else if (state.equals(State.PAST)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_IdAndEndIsBefore(userId,
                    LocalDateTime.now(), sortBy));
        } else if (state.equals(State.FUTURE)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_IdAndStartIsAfter(userId,
                    LocalDateTime.now(), sortBy));
        } else if (state.equals(State.WAITING)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_IdAndStatus(userId,
                    Status.WAITING, sortBy));
        } else if (state.equals(State.REJECTED)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByBooker_IdAndStatus(userId,
                    Status.REJECTED, sortBy));
        } else {
            return null;
        }
    }

    @Override
    public List<BookingDTOResponse> getBookingByOwnerItems(Long userId, String strState) {
        State state = checkStatus(strState);
        userService.getUserById(userId);
        Sort sortBy = Sort.by(Sort.Direction.DESC, "start");

        if (state.equals(State.ALL)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_Id(userId, sortBy));
        } else if (state.equals(State.CURRENT)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_IdAndStartIsBeforeAndEndIsAfter(userId,
                    LocalDateTime.now(), LocalDateTime.now(), sortBy));
        } else if (state.equals(State.PAST)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_IdAndEndIsBefore(userId,
                    LocalDateTime.now(), sortBy));
        } else if (state.equals(State.FUTURE)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_IdAndStartIsAfter(userId,
                    LocalDateTime.now(), sortBy));
        } else if (state.equals(State.WAITING)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_IdAndStatus(userId,
                    Status.WAITING, sortBy));
        } else if (state.equals(State.REJECTED)) {
            return BookingMapper.mapEntityToDTOList(bookingRepository.findAllByItem_Owner_IdAndStatus(userId,
                    Status.REJECTED, sortBy));
        } else {
            return null;
        }
    }

    private State checkStatus(String strState) {
        State state;
        try {
            state = State.valueOf(strState);
        } catch (RuntimeException e) {
            throw new UnsupportedStatusException(strState);
        }
        return state;
    }

}
