package com.example.taskservice.outbox;

import com.example.dto.OutboxEventDto;
import com.example.taskservice.entity.TaskAttempt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {
    private final OutboxRepository repo;
    private final OutboxMapper mapper;

    @Override
    @Transactional
    public void successTask(TaskAttempt attempt) {
        UUID attemptId = attempt.getId();
        UUID userId = attempt.getUserId();
        log.info("CREATE SUCCESS TASK ATTEMPT [{}] EVENT BY USER [{}]",
                attemptId, userId);

        OutboxEvent mapped = mapper.successTask(attempt);
        OutboxEvent saved = repo.save(mapped);

        log.info("SUCCESS TASK ATTEMPT [{}] EVENT [{}] BY USER [{}] IS CREATED.",
                attemptId, saved.getId(), userId);
    }

    @Override
    public List<OutboxEventDto> readAll() {
        Stream<OutboxEvent> stream = Stream.of((OutboxEvent) repo.findAll());
        return stream.map(mapper::read).toList();
    }
}
