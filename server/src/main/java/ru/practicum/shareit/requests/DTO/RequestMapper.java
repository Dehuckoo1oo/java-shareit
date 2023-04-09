package ru.practicum.shareit.requests.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.DTO.ItemForRequestDTO;
import ru.practicum.shareit.item.DTO.ItemMapper;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.requests.Request;
import ru.practicum.shareit.requests.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;


    @Autowired
    public RequestMapper(RequestRepository requestRepository, UserRepository userRepository,
                         ItemRepository itemRepository, ItemMapper itemMapper) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public RequestDTO makeDTOFromRequest(Request request) {
        return new RequestDTO(request.getId(), request.getCreated(), request.getDescription());
    }


    public Request makeRequestFromDTO(RequestDTO requestDTO, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return new Request(requestDTO.getId(), user, requestDTO.getCreated(), requestDTO.getDescription());
    }

    public OwnedRequestDTO makeOwnerDTOFromRequest(Request request) {
        List<Item> items = itemRepository.findItemsByRequestId(request.getId());
        List<ItemForRequestDTO> itemDTOs = new ArrayList<>();
        if (!items.isEmpty()) {
            items.forEach(item -> itemDTOs.add(itemMapper.makeItemForRequestDTO(item)));
        }
        return new OwnedRequestDTO(request.getId(), request.getDescription(), request.getCreated(),
                itemDTOs);
    }

    public List<OwnedRequestDTO> makeOwnerDTOsFromRequests(List<Request> requests) {
        List<OwnedRequestDTO> requestDTOs = new ArrayList<>();
        requests.forEach(request -> requestDTOs.add(makeOwnerDTOFromRequest(request)));
        return requestDTOs;
    }
}
