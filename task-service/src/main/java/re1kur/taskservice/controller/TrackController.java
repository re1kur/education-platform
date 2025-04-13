package re1kur.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re1kur.taskservice.dto.TrackPayload;
import re1kur.taskservice.service.TrackService;

@RestController
@RequestMapping("api/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService service;

    @PostMapping("create")
    public void createTrack(@RequestBody TrackPayload payload) {
        service.create(payload);
    }

}
