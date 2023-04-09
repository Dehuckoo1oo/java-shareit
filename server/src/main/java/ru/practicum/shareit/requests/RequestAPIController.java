package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.DTO.OwnedRequestDTO;
import ru.practicum.shareit.requests.DTO.RequestDTO;

import java.util.List;

@RestController()
@RequestMapping("/requests")
public class RequestAPIController {
    private final RequestService requestService;

    @Autowired
    public RequestAPIController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public RequestDTO createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody RequestDTO requestDTO) {
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
                                        @RequestParam(required = false, defaultValue = "10") String size) {
        return requestService.findAll(from, size, userId);
    }
}
