package com.example.carreg.user.service.impl;

import com.example.carreg.core.impl.CoreCRUDServiceImpl;
import com.example.carreg.user.entity.AppRoleEntity;
import com.example.carreg.user.service.AppRoleService;
import org.springframework.stereotype.Service;

@Service
public class AppRoleServiceImpl extends CoreCRUDServiceImpl<AppRoleEntity> implements AppRoleService {

    @Override
    protected void updateCore(AppRoleEntity updatableEntity, AppRoleEntity entity) {
        updatableEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<AppRoleEntity> getManagedClass() {
        return AppRoleEntity.class;
    }
}
