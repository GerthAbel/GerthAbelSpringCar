package com.example.carreg.core.impl;

import com.example.carreg.core.CoreCRUDService;
import com.example.carreg.core.entity.CoreEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class CoreCRUDServiceImpl<T extends CoreEntity> implements CoreCRUDService<T> {

    @Autowired
    protected EntityManager entityManager;

    public CoreCRUDServiceImpl() {
    }

    @Override
    public List<T> findAll() {
        //JPQL
        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a", getManagedClass()).getResultList();
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        T manufacturerEntity = findById(id);
        if (manufacturerEntity == null) {
            return false;
        }
        entityManager.remove(manufacturerEntity);
        return true;
    }

    @Override
    public T update(T entity) {
        T updateblecar = findById(entity.getId());
        if (updateblecar != null) {
            updateCore(updateblecar, entity);
            entityManager.merge(updateblecar);
        }
        return updateblecar;
    }

    @Override
    public T findById(Long id) {
        // dobhat az eclipseLink: NoResultException
        return entityManager.find(getManagedClass(), id);
    }

    protected abstract void updateCore(T updatableEntity, T entity);

    protected abstract Class<T> getManagedClass();
}
