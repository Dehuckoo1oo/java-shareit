package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NoSuchBodyException;
import ru.practicum.shareit.exception.NotFoundResourceException;
import ru.practicum.shareit.requests.DTO.OwnedRequestDTO;
import ru.practicum.shareit.requests.DTO.RequestDTO;
import ru.practicum.shareit.requests.DTO.RequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final UserService userService;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper,
                              UserService userService) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.userService = userService;
    }

    public RequestDTO create(RequestDTO requestDTO, Long userId) {
        requestDTO.setId(null);
        userService.getUserById(userId);
        Request request = requestMapper.makeRequestFromDTO(requestDTO, userId);
        request = requestRepository.save(request);
        return requestMapper.makeDTOFromRequest(request);
    }

    public List<OwnedRequestDTO> findRequestsByOwner(Long userId) {
        userService.getUserById(userId);
        Sort sortBy = Sort.by(Sort.Direction.DESC, "created");
        List<Request> requests = requestRepository.findAllByUserId(userId, sortBy);
        return requestMapper.makeOwnerDTOsFromRequests(requests);
    }

    public OwnedRequestDTO findRequestByOwnerAndId(Long userId, Long requestId) {
        userService.getUserById(userId);
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) {
            throw new NoSuchBodyException(String.format("Запрос с id %s не найден", requestId));
        }
        return requestMapper.makeOwnerDTOFromRequest(request);
    }

    public List<OwnedRequestDTO> findAll(String from, String size, Long userId) {
        User user = userService.getUserById(userId);
        List<OwnedRequestDTO> requestDTOs = new ArrayList<>();
        if (from == null || size == null) {
            requestRepository.findAllExcept(user).forEach(request -> requestDTOs.add(requestMapper.makeOwnerDTOFromRequest(request)));
            return requestDTOs;
        }
        Sort sortBy = Sort.by(Sort.Direction.DESC, "created");

        Pageable page = makePageable(from, size, sortBy);
        Page<Request> requestPage = requestRepository.findAllExcept(user, page);
        requestPage.getContent().forEach(request -> requestDTOs.add(requestMapper.makeOwnerDTOFromRequest(request)));
        return requestDTOs;
    }

    private Pageable makePageable(String from, String size, Sort sort) {
        int intFrom = Integer.parseInt(from);
        int intSize = Integer.parseInt(size);
        if (intFrom < 0 || intSize < 0) {
            throw new NotFoundResourceException("Не верно заданны ограничения");
        }
        int page = 0;
        if (intFrom != 0) {
            page = intFrom / intSize;
        }
        return PageRequest.of(page, intSize, sort);
    }
}
