package re1kur.taskservice.mapper.func;

import re1kur.taskservice.dto.TrackWriteDto;
import re1kur.taskservice.entity.Track;

import java.util.function.Function;

public class WriteTrackMapFunction implements Function<TrackWriteDto, Track> {
    /**
     * Applies this function to the given argument.
     *
     * @param dto the function argument
     * @return the function result
     */
    @Override
    public Track apply(TrackWriteDto dto) {
        return Track.builder()
                .name(dto.name())
                .build();
    }
}
