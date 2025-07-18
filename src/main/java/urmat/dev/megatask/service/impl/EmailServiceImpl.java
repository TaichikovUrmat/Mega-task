package urmat.dev.megatask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import urmat.dev.megatask.entity.Task;
import urmat.dev.megatask.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.mail.to}")
    private String to;

    @Override
    public void sendTaskCreatedEmail(Task task) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Новая задача создана");
        message.setText("Название: " + task.getTitle() + "\nОписание: " + task.getDescription());

        mailSender.send(message);
    }
}
