package urmat.dev.megatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urmat.dev.megatask.entity.Task;
import urmat.dev.megatask.exceptions.NotFoundException;

public interface TaskRepository extends JpaRepository<Task, Long> {

    default Task findByTaskId(Long taskId) {
        return findById(taskId).orElseThrow(() -> new NotFoundException("Task not found : " + taskId));
    }


}
