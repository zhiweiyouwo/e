package com.loy.upm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loy.e.common.vo.System;
import com.loy.e.security.service.SystemService;
import com.loy.upm.sys.domain.entity.SystemEntity;
import com.loy.upm.sys.repository.SystemRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Service(value = "systemService")
@Transactional
public class SystemServiceImpl implements SystemService {
    @Autowired
    SystemRepository systemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<System> getMySystem(String username) {
        List<SystemEntity> list = systemRepository.querySystemByUsername(username);
        List<System> systems = null;
        if (list != null) {
            systems = new ArrayList<System>();
            for (SystemEntity e : list) {
                System system = new System();
                system.setId(e.getId());
                system.setName(e.getName());
                system.setUrl(e.getUrl());
                system.setCode(e.getCode());
                systems.add(system);
            }
        }
        return systems;
    }

}
