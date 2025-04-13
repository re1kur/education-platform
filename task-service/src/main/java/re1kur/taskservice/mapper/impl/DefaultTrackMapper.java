package re1kur.taskservice.mapper.impl;

import org.springframework.stereotype.Component;
import re1kur.taskservice.dto.TrackPayload;
import re1kur.taskservice.entity.Track;
import re1kur.taskservice.mapper.TrackMapper;

@Component
public class DefaultTrackMapper implements TrackMapper {

    @Override
    public Track write(TrackPayload dto) {
        return Track.builder()
                .name(dto.name())
                .build();
    }
}
