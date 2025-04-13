package re1kur.taskservice.mapper;

import re1kur.taskservice.dto.TrackPayload;
import re1kur.taskservice.entity.Track;

public interface TrackMapper {
    Track write(TrackPayload dto);
}
