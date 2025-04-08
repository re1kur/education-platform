package re1kur.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re1kur.taskservice.entity.Track;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
