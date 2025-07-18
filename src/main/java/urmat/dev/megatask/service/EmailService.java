package urmat.dev.megatask.service;

import urmat.dev.megatask.entity.Task;

public interface EmailService {

   void sendTaskCreatedEmail(Task task);
}
