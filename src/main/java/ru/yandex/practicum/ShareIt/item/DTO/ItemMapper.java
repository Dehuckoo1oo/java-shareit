package ru.yandex.practicum.ShareIt.item.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.ShareIt.booking.Booking;
import ru.yandex.practicum.ShareIt.booking.BookingRepository;
import ru.yandex.practicum.ShareIt.booking.Status;
import ru.yandex.practicum.ShareIt.item.Item;
import ru.yandex.practicum.ShareIt.item.LastOrNextBooking;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;
import ru.yandex.practicum.ShareIt.item.comments.CommentMapper;
import ru.yandex.practicum.ShareIt.item.comments.CommentRepository;
import ru.yandex.practicum.ShareIt.requests.RequestRepository;
import ru.yandex.practicum.ShareIt.requests.Request;
import ru.yandex.practicum.ShareIt.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ItemMapper {

    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public ItemMapper(BookingRepository bookingRepository, CommentRepository commentRepository,
                      CommentMapper commentMapper,RequestRepository requestRepository) {
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.requestRepository = requestRepository;
    }

    public ItemDTO makeItemDtoFromItem(Item item, Long userId) {
        List<Booking> bookings = bookingRepository.findAllByItem_Id(item.getId());
        Booking lastBooking = bookings.stream()
                .filter(booking -> booking.getStart().isBefore(LocalDateTime.now()))
                .filter(booking -> booking.getStatus().equals(Status.APPROVED))
                .filter(booking -> booking.getItem().getOwner().getId().equals(userId))
                .max(Comparator.comparing(Booking::getEnd))
                .orElse(null);
        Booking nextBooking = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .filter(booking -> booking.getStatus().equals(Status.APPROVED))
                .filter(booking -> booking.getItem().getOwner().getId().equals(userId))
                .max(Comparator.comparing(Booking::getEnd))
                .orElse(null);
        List<CommentDTO> comments = new ArrayList<>();
        commentRepository.findAllByItem_id(item.getId())
                .forEach(comment -> comments.add(commentMapper.mapEntityToDTO(comment)));
        Request request = item.getRequest();
        Long requestId = null;
        if(request != null){
            requestId = request.getId();
        }
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                mapToLastOrNextBooking(lastBooking), mapToLastOrNextBooking(nextBooking), comments,requestId);
    }

    public Item makeItemFromItemDTO(ItemDTO itemDTO, User owner) {
        Long requestId = itemDTO.getRequestId();
        Request request = null;
        if(requestId != null){
            request = requestRepository.findById(itemDTO.getRequestId()).orElse(null);
        }
        return new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getDescription(), owner, itemDTO.getAvailable(),
                request);
    }

    public ItemForRequestDTO makeItemForRequestDTO(Item item){
        return new ItemForRequestDTO(item.getId(), item.getName(), item.getDescription(),item.getAvailable(),
                item.getRequest().getId());
    }

    public static LastOrNextBooking mapToLastOrNextBooking(Booking booking) {
        if (booking == null) {
            return null;
        } else {
            return new LastOrNextBooking(booking.getId(), booking.getBooker().getId());
        }
    }

}
