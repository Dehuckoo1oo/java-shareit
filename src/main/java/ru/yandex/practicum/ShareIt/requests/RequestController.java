package ru.yandex.practicum.ShareIt.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ShareIt.groups.Create;
import ru.yandex.practicum.ShareIt.requests.DTO.OwnedRequestDTO;
import ru.yandex.practicum.ShareIt.requests.DTO.RequestDTO;

import java.util.List;

@RestController()
@RequestMapping("/requests")
public class RequestController {
    RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public RequestDTO createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @Validated(Create.class) @RequestBody RequestDTO requestDTO) {
        return requestService.create(requestDTO, userId);
    }

    @GetMapping
    public List<OwnedRequestDTO> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestService.findRequestsByOwner(userId);
    }

    @GetMapping("/{requestId}")
    public OwnedRequestDTO getRequestsByIdAndUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @PathVariable Long requestId) {
        return requestService.findRequestByOwnerAndId(userId, requestId);
    }

    @GetMapping("/all")
    public List<OwnedRequestDTO> getAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                        @RequestParam(required = false, defaultValue = "0") String from,
                                        @RequestParam(required = false, defaultValue = "9999999") String size) {
        return requestService.findAll(from, size, userId);
    }
}
