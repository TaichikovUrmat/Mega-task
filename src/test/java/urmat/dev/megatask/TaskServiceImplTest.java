package urmat.dev.megatask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import urmat.dev.megatask.dto.request.TaskRequest;
import urmat.dev.megatask.entity.Task;
import urmat.dev.megatask.repository.TaskRepository;
import urmat.dev.megatask.service.EmailService;
import urmat.dev.megatask.service.impl.TaskServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void addTask() {
        TaskRequest request = TaskRequest.builder()
                .title("test title")
                .description("test description urmat task")
                .completed(true)
                .build();

        Task saved = Task.builder()
                .id(1L).title("test title")
                .description("test description urmat task")
                .completed(true)
                .build();
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        var response = taskService.addTask(request);
        assertEquals("Task created", response.message());

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(emailService, times(1)).sendTaskCreatedEmail(any(Task.class));
    }

    @Test
    void getAllTask() {
        Task task1 = Task.builder().id(1L).title("Task1").description("Desc1").completed(true).build();
        Task task2 = Task.builder().id(2L).title("Task2").description("Desc2").completed(false).build();

        Page<Task> page = new PageImpl<>(List.of(task1, task2));

        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(page);

        var result = taskService.getAllTask(1, 10);

        assertEquals(2, result.size());
        assertEquals("Task1", result.get(0).title());
        assertEquals("Task2", result.get(1).title());
    }

    @Test
    void getByTaskId_TaskResponse() {
        Task task = Task.builder()
                .id(2L)
                .title("Important")
                .description("Fix bug")
                .completed(false)
                .build();
        when(taskRepository.findByTaskId(2L)).thenReturn(task);

        var response = taskService.getByTaskId(2L);

        assertEquals("Important", response.title());
        assertEquals("Fix bug", response.description());
        assertFalse(response.completed());
    }

    @Test
    void updateByTaskId() {
        Long taskId = 3L;
        Task existingTask = Task.builder()
                .id(taskId)
                .title("Old Title")
                .description("Old Desc")
                .completed(false)
                .build();

        TaskRequest updateRequest = TaskRequest.builder()
                .title("New Title")
                .description("New Desc")
                .completed(true)
                .build();

        when(taskRepository.findByTaskId(taskId)).thenReturn(existingTask);

        var response = taskService.updateByTaskId(taskId, updateRequest);

        assertEquals("Task updated", response.message());
        assertEquals("New Title", existingTask.getTitle());
        assertEquals("New Desc", existingTask.getDescription());
        assertTrue(existingTask.isCompleted());
    }

    @Test
    void deleteByTaskId() {
        Task task = Task.builder()
                .id(3L)
                .title("To delete")
                .description("desc")
                .completed(false)
                .build();

        when(taskRepository.findByTaskId(3L)).thenReturn(task);

        var response = taskService.deleteByTaskId(3L);

        assertEquals("Task  deleted ", response.message());
        verify(taskRepository).delete(task);
    }

}
