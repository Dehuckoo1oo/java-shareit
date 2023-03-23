package ru.yandex.practicum.ShareIt.requests;

import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestDTO;

import java.util.List;

public interface RequestService {
    public RequestDTO create(RequestDTO requestDTO, Long userId);

    public List<OwnedRequestDTO> findRequestsByOwner(Long userId);

    public OwnedRequestDTO findRequestByOwnerAndId(Long userId, Long requestId);

    public List<OwnedRequestDTO> findAll(String from, String size, Long userId);

}
