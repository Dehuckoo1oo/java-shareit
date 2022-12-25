package ru.yandex.practicum.ShareIt.Item;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.User.UserService;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    ItemStorage itemStorage;
    UserService userService;

    public ItemServiceImpl(ItemStorage itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    @Override
    public Item create(Item item, Long userId) {
        item.setOwner(userService.getUserById(userId).getId());
        return itemStorage.create(item);
    }

    @Override
    public Item remove(Item item) {
        return itemStorage.remove(item);
    }

    @Override
    public Item update(Item item, Long userId, Long itemId) {
        item.setId(itemId);
        Item existItem = getItemById(itemId);
        if (!existItem.getOwner().equals(userId)) {
            throw new NoSuchBodyException(String.format("Предмет с id %s не пренадлежит пользователю с id %s",
                    itemId, userId));
        }
        if (item.getName() != null) {
            existItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existItem.setAvailable(item.getAvailable());
        }
        return itemStorage.update(existItem);
    }

    @Override
    public List<Item> getItemByUserId(Long ownerId) {
        userService.getUserById(ownerId);
        return itemStorage.getItemsByUserId(ownerId);
    }

    @Override
    public Item getItemById(Long id) {
        Optional<Item> item = itemStorage.getItemById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new NoSuchBodyException(String.format("Предмет с id %s отсутствует", id));
        }
    }

    @Override
    public List<Item> findItemsByText(String text) {
        if(text.isBlank()){
            return new ArrayList<>();
        }
        List<Item> items = itemStorage.getAllItems().stream().filter(Item::getAvailable)
                .filter(item -> item
                        .getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        return items;
    }
}
