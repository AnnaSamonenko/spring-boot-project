package com.gmail.samonenko.repository;

import com.gmail.samonenko.model.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TaskDAO implements GenericDAO<Task, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Task read(Long id) {
        return em.find(Task.class, id);
    }

    @Override
    public Task update(Task task) {
        return em.merge(task);
    }

    @Override
    public void create(Task task) {
        em.persist(task);
    }

    @Override
    public void delete(Long id) {
        Task task = em.find(Task.class, id);
        em.remove(task);
    }

    public List<Task> getAll() {
        Query query = em.createQuery("SELECT t FROM Task t");
        return query.getResultList();
    }
}
