package ru.yandex.practicum.ShareIt.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ShareIt.exception.NoSuchBodyException;
import ru.yandex.practicum.ShareIt.exception.NotFoundResourceException;
import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestMapper;
import ru.yandex.practicum.ShareIt.user.User;
import ru.yandex.practicum.ShareIt.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    UserService userService;
    RequestRepository requestRepository;
    RequestMapper requestMapper;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper,
                              UserService userService) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.userService = userService;
    }

    public RequestDTO create(RequestDTO requestDTO, Long userId) {
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
        /*if(!request.getUser().getId().equals(userId)){
            throw new NoSuchBodyException(String.format("Запрос с id %s не пренадлежит пользователю с id %s",
                    requestId, userId));
        }*/
        return requestMapper.makeOwnerDTOFromRequest(request);
    }

    public List<OwnedRequestDTO> findAll(String from, String size, Long userId) {
        User user = userService.getUserById(userId);
        List<OwnedRequestDTO> requestDTOs = new ArrayList<>();
        if(from == null || size == null){
            requestRepository.findAllExcept(user).forEach(request -> requestDTOs.add(requestMapper.makeOwnerDTOFromRequest(request)));
            return requestDTOs;
        }
        int intFrom = Integer.parseInt(from);
        int intSize = Integer.parseInt(size);
        if (intFrom < 0 || intSize < 0) {
            throw new NotFoundResourceException("Не верно заданны ограничения");
        }
        Sort sortBy = Sort.by(Sort.Direction.DESC, "created");
        Pageable page = PageRequest.of(intFrom, intSize, sortBy);
        Page<Request> requestPage = requestRepository.findAllExcept(user,page);
        requestPage.getContent().forEach(request -> requestDTOs.add(requestMapper.makeOwnerDTOFromRequest(request)));
        return requestDTOs;
    }
}
