package com.example.carreg.user.service.impl;

import com.example.carreg.core.impl.CoreCRUDServiceImpl;
import com.example.carreg.user.entity.AppUserEntity;
import com.example.carreg.user.service.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;

@Service
public class AppUserServiceImpl extends CoreCRUDServiceImpl<AppUserEntity> implements AppUserService {
    @Override
    protected void updateCore(AppUserEntity updatableEntity, AppUserEntity entity) {
        updatableEntity.setPassword(entity.getPassword());
        updatableEntity.setUsername(entity.getUsername());
        updatableEntity.setAuthorities(entity.getAuthorities());

    }

    @Override
    protected Class<AppUserEntity> getManagedClass() {
        return AppUserEntity.class;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            TypedQuery<AppUserEntity> query = entityManager.createNamedQuery(AppUserEntity.FIND_USER_BY_USER_NAME, AppUserEntity.class);
            query.setParameter("username", username);

            return query.getSingleResult();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Nem található felhasználónév: " + username);
        }

    }
}
