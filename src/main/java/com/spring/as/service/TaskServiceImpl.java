package com.spring.as.service;

import com.spring.as.repository.TaskDAO;
import com.spring.as.dto.TaskDTO;
import com.spring.as.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@PropertySource("classpath:validation.properties")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private Environment env;

    @Override
    public Task createTask(TaskDTO taskDTO) {
        if (!projectService.isProjectPresent(taskDTO.getProjectName()))
            throw new IllegalArgumentException(env.getProperty("project.not_found"));
        Task task = new Task();
        task.setDate(LocalDate.now());
        task.setDeadline(taskDTO.getDeadline());
        task.setTitle(taskDTO.getTitle());
        task.setProject(projectService.findProjectByProjectName(taskDTO.getProjectName()));
        taskDAO.create(task);
        return task;
    }

    @Override
    public Task getTask(long id) {
        verifyingOfTaskPresence(id);
        return taskDAO.read(id);
    }

    @Override
    public void deleteTask(long id) {
        verifyingOfTaskPresence(id);
        taskDAO.delete(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.getAll();
    }

    private void verifyingOfTaskPresence(long id) {
        if (taskDAO.read(id) == null)
            throw new IllegalArgumentException(env.getProperty("task.not_fount") + " " + id);
    }
}
