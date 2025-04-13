package re1kur.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re1kur.taskservice.dto.TrackPayload;
import re1kur.taskservice.mapper.TrackMapper;
import re1kur.taskservice.repository.TrackRepository;
import re1kur.taskservice.service.TrackService;

@Component
@RequiredArgsConstructor
public class DefaultTrackService implements TrackService {
    private final TrackRepository repo;
    private final TrackMapper mapper;

    @Override
    @Transactional
    public void create(TrackPayload dto) {
        repo.save(mapper.write(dto));
    }
}
