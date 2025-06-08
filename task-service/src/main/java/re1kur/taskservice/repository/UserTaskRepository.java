package re1kur.taskservice.repository;

import java.util.UUID;

public interface UserTaskRepository {

    void addUsersFile(Integer taskId, UUID userId, UUID fileId);

}
