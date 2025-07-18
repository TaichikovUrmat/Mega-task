package urmat.dev.megatask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urmat.dev.megatask.dto.response.SimpleResponse;
import urmat.dev.megatask.dto.request.TaskRequest;
import urmat.dev.megatask.dto.response.TaskResponse;
import urmat.dev.megatask.entity.Task;
import urmat.dev.megatask.repository.TaskRepository;
import urmat.dev.megatask.service.EmailService;
import urmat.dev.megatask.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

     private final TaskRepository taskRepository;
     private final EmailService emailService;

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public SimpleResponse addTask(TaskRequest taskRequest) {
       Task task = new Task();
       task.setTitle(taskRequest.getTitle());
       task.setDescription(taskRequest.getDescription());
       task.setCompleted(taskRequest.isCompleted());
        Task savedTask = taskRepository.save(task);
        emailService.sendTaskCreatedEmail(savedTask);
        return SimpleResponse.
                builder()
                .httpStatus(HttpStatus.OK)
                .message("Task created")
                .build();
    }


    @Override
    @Cacheable(value = "tasks", key = "'all_tasks_' + #pageNumber + '_' + #pageSize")
    public List<TaskResponse> getAllTask(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return taskRepository.findAll(pageRequest)
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.isCompleted(),
                        task.getCreatedAt()
                ))
                .toList();
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponse getByTaskId(Long taskId) {
        Task byTaskId = taskRepository.findByTaskId(taskId);
        return TaskResponse.builder()
                .taskId(byTaskId.getId())
                .title(byTaskId.getTitle())
                .description(byTaskId.getDescription())
                .completed(byTaskId.isCompleted())
                .createdAt(byTaskId.getCreatedAt())
                .build();
    }


    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public SimpleResponse updateByTaskId(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findByTaskId(taskId);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Task updated")
                .build();
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public SimpleResponse deleteByTaskId(Long taskId) {
        Task byTaskId = taskRepository.findByTaskId(taskId);
        taskRepository.delete(byTaskId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Task  deleted ") .build();
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public List<TaskResponse> getAllTask2() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.isCompleted(),
                        task.getCreatedAt()
                ))
                .toList();
    }
}
