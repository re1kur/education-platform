package re1kur.taskservice.mapper;

import re1kur.taskservice.dto.TrackWriteDto;
import re1kur.taskservice.entity.Track;

public interface TrackMapper {
    Track write(TrackWriteDto dto);
}
