package com.gmail.samonenko.bootstrap;

import com.gmail.samonenko.model.Project;
import com.gmail.samonenko.model.Status;
import com.gmail.samonenko.model.Task;
import com.gmail.samonenko.model.User;
import com.gmail.samonenko.repository.ProjectDAO;
import com.gmail.samonenko.repository.TaskDAO;
import com.gmail.samonenko.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String EMAIL = "anna_samonenko@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USERNAME = "anna_samonenko";
    private static final String PROJECT_NAME_1 = "Trainings";
    private static final String PROJECT_NAME_2 = "Books";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadUsers();
    }

    private void loadUsers() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user1 = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .enabled(true)
                .role("USER_ROLE")
                .build();

        userDAO.create(user1);
        addProject(user1);
    }

    @Transactional
    private void addProject(User user) {
        Project project1 = Project.builder().name(PROJECT_NAME_1).user(user).build();
        projectDAO.create(project1);
        createTask(project1, "Java 8 training");
        createTask(project1, "AWS training");

        Project project2 = Project.builder().name(PROJECT_NAME_2).user(user).build();
        projectDAO.create(project2);
        createTask(project2, "Domain Driven Development");
        createTask(project2, "Spring Boot in Action");
    }

    @Transactional
    private void createTask(Project project, String title) {
        Task task = Task.builder().title(title)
                .date(LocalDate.now())
                .deadline(LocalDate.now().plusDays(1))
                .project(project)
                .status(Status.NEW)
                .build();

        taskDAO.create(task);
    }

}
