package com.example.carreg.user.service;

import com.example.carreg.core.CoreCRUDService;
import com.example.carreg.user.entity.AppUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends CoreCRUDService<AppUserEntity>, UserDetailsService {
}
