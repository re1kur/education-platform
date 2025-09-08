//package com.example.taskservice.service;
//
//import com.example.dto.TaskDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import com.example.taskservice.entity.Task;
//import com.example.taskservice.mapper.impl.DefaultTaskMapper;
//import com.example.taskservice.repository.TaskRepository;
//import com.example.taskservice.repository.UserTaskRepository;
//import com.example.taskservice.service.impl.DefaultTaskService;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultTaskServiceTest {
//    @InjectMocks
//    private DefaultTaskService service;
//
//    @Mock
//    private TaskRepository repo;
//
//    @Mock
//    private DefaultTaskMapper mapper;
//
//    @Mock
//    private UserTaskRepository userTaskRepo;
//
//    @Test
//    void testGetList() {
//        Task task1 = Mockito.mock(Task.class);
//        Task task2 = Mockito.mock(Task.class);
//        List<Task> tasks = List.of(task1, task2);
//        TaskDto dto1 = new TaskDto(1, 1, "asd", "asd", 1, 15);
//        TaskDto dto2 = new TaskDto(2, 2, "asd", "asd", 1, 15);
//        List<TaskDto> expected = List.of(dto1, dto2);
//
//        Mockito.when(repo.findAll()).thenReturn(tasks);
//
//        Mockito.when(mapper.read(task1)).thenReturn(dto1);
//        Mockito.when(mapper.read(task2)).thenReturn(dto2);
//
//        List<TaskDto> result = service.getList();
//
//        Assertions.assertEquals(result, expected);
//
//        Mockito.verify(repo, Mockito.times(1)).findAll();
//        Mockito.verify(mapper, Mockito.times(2)).read(Mockito.any(Task.class));
//    }
//
//    @Test
//    void testCreate__ValidCategory__DoesNotThrowException() throws TrackNotFoundException {
//        TaskPayload payload = new TaskPayload(
//                1,
//                "taskName",
//                "desc",
//                1,
//                new BigDecimal("15"));
//        Task task = Task.builder()
//                .track(Mockito.mock(Track.class))
//                .name("taskName")
//                .description("desc")
//                .level(1)
//                .cost(new BigDecimal("15"))
//                .build();
//
//        Mockito.when(mapper.write(payload)).thenReturn(task);
//
//        Assertions.assertDoesNotThrow(() -> service.create(payload));
//
//        Mockito.verify(mapper, Mockito.times(1)).write(payload);
//        Mockito.verify(repo, Mockito.times(1)).save(task);
//    }
//
//    @Test
//    void testCreate__TaskWithNotExistingTrack__ThrowTrackNotFoundException() throws TrackNotFoundException {
//        TaskPayload payload = new TaskPayload(
//                1,
//                "taskName",
//                "desc",
//                1,
//                new BigDecimal("15"));
//
//        Mockito.when(mapper.write(payload)).thenThrow(TrackNotFoundException.class);
//
//        Assertions.assertThrows(TrackNotFoundException.class, () -> service.create(payload));
//
//        Mockito.verify(mapper, Mockito.times(1)).write(payload);
//        Mockito.verifyNoInteractions(repo);
//    }
//
//    @Test
//    void testUpdate__ValidTask__DoesNotThrowException() throws TrackNotFoundException {
//        TaskUpdatePayload payload = new TaskUpdatePayload(
//                1,
//                1,
//                "taskName",
//                "desc",
//                1,
//                new BigDecimal("15"));
//        Task task = Task.builder()
//                .id(1)
//                .track(Mockito.mock(Track.class))
//                .name("taskName")
//                .description("desc")
//                .level(1)
//                .cost(new BigDecimal("15"))
//                .build();
//        Task taskUpdated = Task.builder()
//                .id(1)
//                .track(Mockito.mock(Track.class))
//                .name("taskNameUpdate")
//                .description("desc")
//                .level(1)
//                .cost(new BigDecimal("15"))
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(task));
//        Mockito.when(mapper.update(task, payload)).thenReturn(taskUpdated);
//
//        Assertions.assertDoesNotThrow(() -> service.update(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verify(mapper, Mockito.times(1)).update(task, payload);
//    }
//
//    @Test
//    void testUpdate__NotExistingTask__ThrowTaskNotFoundException() {
//        TaskUpdatePayload payload = new TaskUpdatePayload(
//                1,
//                1,
//                "taskName",
//                "desc",
//                1,
//                new BigDecimal("15"));
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(TaskNotFoundException.class, () -> service.update(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verifyNoInteractions(mapper);
//    }
//
//    @Test
//    void testUpdate__TaskPayloadWithNotExistingTrack__ThrowTrackNotFoundException() throws TrackNotFoundException {
//        TaskUpdatePayload payload = new TaskUpdatePayload(
//                1,
//                123,
//                "taskName",
//                "desc",
//                1,
//                new BigDecimal("15"));
//        Task task = Task.builder()
//                .id(1)
//                .track(Mockito.mock(Track.class))
//                .name("taskName")
//                .description("desc")
//                .level(1)
//                .cost(new BigDecimal("15"))
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(task));
//        Mockito.when(mapper.update(task, payload)).thenThrow(TrackNotFoundException.class);
//
//        Assertions.assertThrows(TrackNotFoundException.class, () -> service.update(payload));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verify(mapper, Mockito.times(1)).update(task, payload);
//        Mockito.verify(repo, Mockito.times(0)).save(Mockito.any(Task.class));
//    }
//
//    @Test
//    void testDelete__ValidTask__DoesNotThrowException() {
//        Integer taskId = 1;
//        Task task = Task.builder()
//                .id(1)
//                .track(Mockito.mock(Track.class))
//                .name("taskName")
//                .description("desc")
//                .level(1)
//                .cost(new BigDecimal("15"))
//                .build();
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(task));
//        Mockito.doNothing().when(repo).delete(task);
//
//        Assertions.assertDoesNotThrow(() -> service.delete(taskId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verify(repo, Mockito.times(1)).delete(task);
//    }
//
//    @Test
//    void testDelete__NotExistingTask__ThrowTaskNotFoundException() {
//        Integer taskId = 1;
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(TaskNotFoundException.class, () -> service.delete(taskId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verifyNoMoreInteractions(repo);
//    }
//
//    @Test
//    void get__ValidTask__DoesNotThrowException() {
//        Integer taskId = 1;
//        Task task = Mockito.mock(Task.class);
//        TaskDto dto = Mockito.mock(TaskDto.class);
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(task));
//        Mockito.when(mapper.read(task)).thenReturn(dto);
//
//        Assertions.assertDoesNotThrow(() -> service.getById(taskId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verify(mapper, Mockito.times(1)).read(task);
//    }
//
//    @Test
//    void get__NotExistingTask__ThrowTaskNotFoundException() {
//        Integer taskId = 1;
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(TaskNotFoundException.class, () -> service.getById(taskId));
//
//        Mockito.verify(repo, Mockito.times(1)).findById(1);
//        Mockito.verifyNoInteractions(mapper);
//    }
//
////    @Test
////    void testGetPage_ReturnsFilteredPage() {
////        Pageable pageable = PageRequest.of(0, 10);
////        TaskFilter filter = new TaskFilter("Task1", 123, 5, new BigDecimal("100"));
////        Page<Task> taskPage = Mockito.mock(Page.class);
////        Page<TaskDto> dtoPage = Mockito.mock(Page.class);
////
////        Mockito.when(repo.findAllByFilter(pageable, "Task1", new BigDecimal("100"), 5))
////                .thenReturn(taskPage);
////        Mockito.when(taskPage.map(Mockito.any())).thenReturn(dtoPage);
////
////        Mockito.when(dtoPage.getNumber()).thenReturn(0);
////        Mockito.when(dtoPage.getSize()).thenReturn(10);
////        Mockito.when(dtoPage.getTotalElements()).thenReturn(1L);
////        Mockito.when(dtoPage.getContent()).thenReturn(List.of(Mockito.mock(TaskDto.class)));
////
////        TaskPageDto result = service.getPage(pageable, filter);
////
////        Assertions.assertNotNull(result);
////        Mockito.verify(repo).findAllByFilter(pageable, "Task1", new BigDecimal("100"), 5);
////    }
//
//    @Test
//    void testAttachFile__DoesNotThrowException() throws TaskNotFoundException {
//        String checkedBy = "550e8400-e29b-41d4-a716-446655440000";
//        String fileId = "550e8400-e29b-41d4-a716-446655440001";
//        int taskId = 1;
//        Task task = Mockito.mock(Task.class);
//
//        Mockito.when(repo.findById(taskId)).thenReturn(Optional.of(task));
//
//        service.attachFile(checkedBy, taskId, fileId);
//
//        Mockito.verify(repo).findById(taskId);
//        Mockito.verify(userTaskRepo).addUsersFile(
//                taskId,
//                UUID.fromString(checkedBy),
//                UUID.fromString(fileId)
//        );
//    }
//
//    @Test
//    void testAttachFile__NotExistingTask__ThrowTaskNotFoundException() {
//        String checkedBy = "550e8400-e29b-41d4-a716-446655440000";
//        String fileId = "550e8400-e29b-41d4-a716-446655440001";
//        int taskId = 999;
//
//        Mockito.when(repo.findById(taskId)).thenReturn(Optional.empty());
//
//        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () ->
//                service.attachFile(checkedBy, taskId, fileId));
//
//        Assertions.assertEquals("Task with id 999 does not exist.", exception.getMessage());
//        Mockito.verify(repo).findById(taskId);
//        Mockito.verifyNoInteractions(userTaskRepo);
//    }
//
//    @Test
//    void testAttachFile__ExceptionInUserTaskRepo__ThrowInternalServerErrorException() {
//        String checkedBy = "550e8400-e29b-41d4-a716-446655440000";
//        String fileId = "invalid-uuid";
//        int taskId = 1;
//        Task task = Mockito.mock(Task.class);
//
//        Mockito.when(repo.findById(1)).thenReturn(Optional.of(task));
//
//        InternalServerErrorException exception = Assertions.assertThrows(InternalServerErrorException.class, () ->
//                service.attachFile(checkedBy, taskId, fileId));
//
//        Assertions.assertTrue(exception.getMessage().contains("Invalid UUID"));
//    }
//}