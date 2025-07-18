package urmat.dev.megatask.api;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import urmat.dev.megatask.dto.response.SimpleResponse;
import urmat.dev.megatask.dto.request.TaskRequest;
import urmat.dev.megatask.dto.response.TaskResponse;
import urmat.dev.megatask.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TaskApi {

    private final TaskService taskService;

    @PostMapping("/addTask")
    public SimpleResponse create(@RequestBody TaskRequest taskRequest) {
        log.info("Creating task: {}", taskRequest.getTitle());
        return taskService.addTask(taskRequest);
    }

    // todo : из ( Postman и Броузер )  http://localhost:8080/api/tasks/getAllTask?pageNumber=1&pageSize=10
    @GetMapping("/getAllTask")
    public List<TaskResponse> getAllTasks(@RequestParam(defaultValue = "1") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        log.info("Getting all tasks - page: {}, size: {}", pageNumber, pageSize);
        return taskService.getAllTask(pageNumber, pageSize);
    }

    // todo :  из  Броузера
    @GetMapping("/getAll")
    public List<TaskResponse> getAllTasks() {
        log.info("Getting all tasks ");
        return taskService.getAllTask2();
    }

    @GetMapping("/getByTaskId/{taskId}")
    public TaskResponse getByTaskId(@PathVariable Long taskId) {
        log.info("Getting task by id: {}", taskId);
        return taskService.getByTaskId(taskId);
    }

    @PutMapping("/updateTaskId/{taskId}")
    public SimpleResponse updateByTaskId(@PathVariable Long taskId, @RequestBody TaskRequest taskRequest) {
        log.info("Updating task id: {}", taskId);
        return taskService.updateByTaskId(taskId, taskRequest);
    }

    @DeleteMapping("/delete/{taskId}")
    public SimpleResponse deleteByTaskId(@PathVariable Long taskId) {
        log.info("Deleting task id: {}", taskId);
        return taskService.deleteByTaskId(taskId);
    }

}
