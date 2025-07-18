package urmat.dev.megatask.service;

import urmat.dev.megatask.dto.response.SimpleResponse;
import urmat.dev.megatask.dto.request.TaskRequest;
import urmat.dev.megatask.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    SimpleResponse addTask(TaskRequest taskRequest);
    List<TaskResponse> getAllTask( int pageNumber,int pageSize);
    TaskResponse getByTaskId(Long taskId);
    SimpleResponse updateByTaskId(Long taskId, TaskRequest taskRequest);
    SimpleResponse deleteByTaskId(Long taskId);
    List<TaskResponse> getAllTask2();
}
