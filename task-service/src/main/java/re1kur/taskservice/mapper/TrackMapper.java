package re1kur.taskservice.mapper;

import payload.TrackPayload;
import re1kur.taskservice.entity.Track;

public interface TrackMapper {
    Track write(TrackPayload dto);
}
