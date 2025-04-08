package re1kur.taskservice.mapper;

import org.springframework.stereotype.Component;
import re1kur.taskservice.dto.TrackWriteDto;
import re1kur.taskservice.entity.Track;
import re1kur.taskservice.mapper.func.WriteTrackMapFunction;

@Component
public class DefaultTrackMapper implements TrackMapper {
    private final WriteTrackMapFunction mapWriteTrack = new WriteTrackMapFunction();

    @Override
    public Track write(TrackWriteDto dto) {
        return mapWriteTrack.apply(dto);
    }
}
