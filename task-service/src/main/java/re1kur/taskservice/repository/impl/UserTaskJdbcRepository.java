package re1kur.taskservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import re1kur.taskservice.repository.UserTaskRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserTaskJdbcRepository implements UserTaskRepository {
    private final JdbcTemplate template;

    private final static String ADD_USERS_FILE_QUERY = """
                    INSERT INTO user_tasks_files
            (task_id, user_id, file_id)
            VALUES (?, ?, ?)
            ON CONFLICT DO NOTHING;
            """;

    @Override
    public void addUsersFile(Integer taskId, UUID userId, UUID fileId) {
        template.update(ADD_USERS_FILE_QUERY, taskId, userId, fileId);
    }
}
