package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.exception.NoSuchBodyException;
import ru.practicum.shareit.exception.UnsupportedStatusException;
import ru.practicum.shareit.item.DTO.ItemDTO;
import ru.practicum.shareit.item.DTO.ItemMapper;
import ru.practicum.shareit.item.comments.Comment;
import ru.practicum.shareit.item.comments.CommentDTO;
import ru.practicum.shareit.item.comments.CommentMapper;
import ru.practicum.shareit.item.comments.CommentRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;


    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, ItemMapper itemMapper,
                           CommentRepository commentRepository, CommentMapper commentMapper,
                           BookingRepository bookingRepository) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO, Long userId) {
        itemDTO.setId(null);
        Item item = itemMapper.makeItemFromItemDTO(itemDTO, userService.getUserById(userId));
        item = itemRepository.save(item);
        return itemMapper.makeItemDtoFromItem(item, userId);
    }


    @Override
    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId) {

        Item existItem = getItemById(itemId);
        if (!existItem.getOwner().getId().equals(userId)) {
            throw new NoSuchBodyException(String.format("Предмет с id %s не пренадлежит пользователю с id %s",
                    itemId, userId));
        }
        if (itemDTO.getName() != null) {
            existItem.setName(itemDTO.getName());
        }
        if (itemDTO.getDescription() != null) {
            existItem.setDescription(itemDTO.getDescription());
        }
        if (itemDTO.getAvailable() != null) {
            existItem.setAvailable(itemDTO.getAvailable());
        }
        return itemMapper.makeItemDtoFromItem(itemRepository.save(existItem), userId);
    }

    @Transactional
    @Override
    public List<ItemDTO> getItemByUserId(Long ownerId) {
        User owner = userService.getUserById(ownerId);
        List<Item> items = owner.getItems();
        List<ItemDTO> itemsDTO = new ArrayList<>();
        items.forEach(item -> itemsDTO.add(itemMapper.makeItemDtoFromItem(item, ownerId)));
        itemsDTO.sort(Comparator.comparing(ItemDTO::getId));
        return itemsDTO;
    }

    @Override
    public ItemDTO findItemById(Long userId, Long id) {
        return itemMapper.makeItemDtoFromItem(getItemById(id), userId);
    }

    public Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findItemById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NoSuchBodyException(String.format("Предмет с id %s отсутствует", id));
        }
    }

    @Override
    public List<ItemDTO> findItemsByText(String text, Long userId) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findAll().stream().filter(Item::getAvailable)
                .filter(item -> item
                        .getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        List<ItemDTO> itemsDTO = new ArrayList<>();
        items.forEach(item -> itemsDTO.add(itemMapper.makeItemDtoFromItem(item, userId)));
        return itemsDTO;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long userId, Long itemId) {
        Booking bookingExist = bookingRepository.findAllByItem_Id(itemId).stream()
                .filter(booking -> booking.getBooker().getId().equals(userId))
                .filter(booking -> booking.getStart().isBefore(LocalDateTime.now()))
                .filter(booking -> booking.getStatus().equals(Status.APPROVED))
                .findFirst()
                .orElseThrow(() -> {
                    throw new UnsupportedStatusException(
                            String.format("Данный пользователь не брал в аренду предмет с id %s", itemId));
                });

        commentDTO.setCreated(LocalDateTime.now());
        Comment comment = commentMapper.mapDTOToEntity(commentDTO, userId, itemId);
        return commentMapper.mapEntityToDTO(commentRepository.save(comment));
    }
}
