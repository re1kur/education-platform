package re1kur.taskservice.service;

import re1kur.taskservice.dto.TrackWriteDto;

public interface TrackService {
    void create(TrackWriteDto dto);
}
