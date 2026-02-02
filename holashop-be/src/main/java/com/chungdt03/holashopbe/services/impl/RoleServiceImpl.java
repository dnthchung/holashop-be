package com.chungdt03.holashopbe.services.impl;

import com.chungdt03.holashopbe.models.Role;
import com.chungdt03.holashopbe.repositories.RoleRepository;
import com.chungdt03.holashopbe.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
