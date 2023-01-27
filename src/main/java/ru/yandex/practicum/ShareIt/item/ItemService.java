package ru.yandex.practicum.ShareIt.item;

import ru.yandex.practicum.ShareIt.item.DTO.ItemDTO;
import ru.yandex.practicum.ShareIt.item.comments.CommentDTO;

import java.util.List;

public interface ItemService {

    public ItemDTO create(ItemDTO itemDTO, Long userId);

    public ItemDTO remove(ItemDTO itemDTO, Long owner);

    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId);

    public List<ItemDTO> getItemByUserId(Long ownerId);

    public ItemDTO findItemById(Long UserId, Long id);

    public List<ItemDTO> findItemsByText(String text, Long userId);

    public Item getItemById(Long id);

    public CommentDTO createComment(CommentDTO commentDTO, Long userId, Long itemId);

}
