package ru.practicum.shareit.item;

import ru.practicum.shareit.item.DTO.ItemDTO;
import ru.practicum.shareit.item.comments.CommentDTO;

import java.util.List;

public interface ItemService {

    public ItemDTO create(ItemDTO itemDTO, Long userId);

    public ItemDTO update(ItemDTO itemDTO, Long userId, Long itemId);

    public List<ItemDTO> getItemByUserId(Long ownerId);

    public ItemDTO findItemById(Long userId, Long id);

    public List<ItemDTO> findItemsByText(String text, Long userId);

    public Item getItemById(Long id);

    public CommentDTO createComment(CommentDTO commentDTO, Long userId, Long itemId);

}