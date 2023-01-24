package ru.yandex.practicum.ShareIt.item;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.booking.Booking;
import ru.yandex.practicum.ShareIt.booking.BookingRepository;
import ru.yandex.practicum.ShareIt.booking.Status;
import ru.yandex.practicum.ShareIt.exception.UnsupportedStatusException;
import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.DTO.ItemMapper;
import ru.yandex.practicum.ShareIt.item.comments.*;
import ru.yandex.practicum.ShareIt.user.User;
import ru.yandex.practicum.ShareIt.user.UserService;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;

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
        Item item = ItemMapper.makeItemFromItemDTO(itemDTO, userService.getUserById(userId));
        return itemMapper.makeItemDtoFromItem(itemRepository.save(item), userId);
    }

    @Override
    public ItemDTO remove(ItemDTO itemDTO, Long userId) {
        Item existItem = getItemById(itemDTO.getId());
        if (existItem.getOwner().getId().equals(userId)) {
            itemRepository.delete(existItem);
            return itemMapper.makeItemDtoFromItem(existItem, userId);
        } else {
            throw new NoSuchBodyException(String.format("Предмет с id %s не пренадлежит пользователю с id %s",
                    existItem.getId(), userId));
        }
    }

    @Override
    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId) {

        //itemDTO.setId(itemId);
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

        Comment comment = commentMapper.mapDTOToEntity(commentDTO, userId, itemId);
        return commentMapper.mapEntityToDTO(commentRepository.save(comment));
    }
}
