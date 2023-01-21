package ru.yandex.practicum.ShareIt.item;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.user.UserService;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO, Long userId) {
        Item item = ItemMapper.makeItemFromItemDTO(itemDTO, userService.getUserById(userId).getId());
        return ItemMapper.makeItemDtoFromItem(itemRepository.save(item));
    }

    @Override
    public ItemDTO remove(ItemDTO itemDTO, Long userId) {
        Item existItem = getItemById(itemDTO.getId());
        if (existItem.getOwner().equals(userId)) {
            itemRepository.delete(existItem);
            return ItemMapper.makeItemDtoFromItem(existItem);
        } else {
            throw new NoSuchBodyException(String.format("Предмет с id %s не пренадлежит пользователю с id %s",
                    existItem.getId(), userId));
        }
    }

    @Override
    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId) {

        //itemDTO.setId(itemId);
        Item existItem = getItemById(itemId);
        if (!existItem.getOwner().equals(userId)) {
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
        return ItemMapper.makeItemDtoFromItem(itemRepository.save(existItem));
    }

    @Override
    public List<ItemDTO> getItemByUserId(Long ownerId) {
        userService.getUserById(ownerId);
        List<Item> items = itemRepository.findItemByOwner(ownerId);
        List<ItemDTO> itemsDTO = new ArrayList<>();
        items.forEach(item -> itemsDTO.add(ItemMapper.makeItemDtoFromItem(item)));
        return itemsDTO;
    }

    @Override
    public ItemDTO findItemById(Long id) {
        return ItemMapper.makeItemDtoFromItem(getItemById(id));
    }

    private Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findItemById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NoSuchBodyException(String.format("Предмет с id %s отсутствует", id));
        }
    }

    @Override
    public List<ItemDTO> findItemsByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findAll().stream().filter(Item::getAvailable)
                .filter(item -> item
                        .getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        List<ItemDTO> itemsDTO = new ArrayList<>();
        items.forEach(item -> itemsDTO.add(ItemMapper.makeItemDtoFromItem(item)));
        return itemsDTO;
    }
}
