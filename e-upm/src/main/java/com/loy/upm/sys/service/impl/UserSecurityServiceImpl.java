package com.loy.upm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.common.tree.TreeUtil;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.vo.Permission;
import com.loy.e.security.vo.User;
import com.loy.upm.sys.domain.entity.MenuI18nEntity;
import com.loy.upm.sys.domain.entity.ResourceEntity;
import com.loy.upm.sys.domain.entity.UserEntity;
import com.loy.upm.sys.repository.MenuI18nRepository;
import com.loy.upm.sys.repository.ResourceRepository;
import com.loy.upm.sys.repository.UserRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Service(value = "securityUserService")
@Transactional
public class UserSecurityServiceImpl implements SecurityUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    MenuI18nRepository menuI18nRepository;

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        User user = null;
        if (userEntity != null) {
            user = new User();
            user.setId(userEntity.getId());
            user.setUsername(username);
            user.setName(userEntity.getName());
            user.setPassword(userEntity.getPassword());
            user.setPhoto(userEntity.getPhotoData() != null ? true : false);
        }
        return user;
    }

    @Override
    public List<Permission> getAllPermissions(String systemCode) {

        List<ResourceEntity> list = resourceRepository.findResourceBySystemCode(systemCode);
        List<Permission> permissions = null;
        if (list != null) {
            permissions = new ArrayList<Permission>();
            for (ResourceEntity r : list) {
                Permission permission = new Permission();
                permission.setAccessCode(r.getAccessCode());
                permission.setUrl(r.getUrl());
                permissions.add(permission);
            }
        }

        return permissions;
    }

    @Override
    public List<Permission> getAllPermissions() {

        List<ResourceEntity> list = resourceRepository.findAllResource();
        List<Permission> permissions = null;
        if (list != null) {
            permissions = new ArrayList<Permission>();
            for (ResourceEntity r : list) {
                Permission permission = new Permission();
                permission.setAccessCode(r.getAccessCode());
                permission.setUrl(r.getUrl());
                permissions.add(permission);
            }
        }

        return permissions;
    }

    @Override
    public List<Permission> findPermissionsByUsername(String username, String systemCode) {

        List<ResourceEntity> list = resourceRepository.findResourceByUsernameAndCode(username,
                systemCode);

        List<Permission> permissions = null;
        if (list != null) {
            permissions = new ArrayList<Permission>();
            for (ResourceEntity r : list) {
                Permission permission = new Permission();
                permission.setAccessCode(r.getAccessCode());
                permission.setUrl(r.getUrl());
                permissions.add(permission);
            }
        }

        return permissions;
    }

    @Override
    public List<Permission> findPermissionsByUsername(String username) {

        List<ResourceEntity> list = resourceRepository.findResourceByUsername(username);

        List<Permission> permissions = null;
        if (list != null) {
            permissions = new ArrayList<Permission>();
            for (ResourceEntity r : list) {
                Permission permission = new Permission();
                permission.setAccessCode(r.getAccessCode());
                permission.setUrl(r.getUrl());
                permissions.add(permission);
            }
        }

        return permissions;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<TreeNode> getMenuByUsername(String userId, String systemCode, String lang) {
        List<ResourceEntity> list = resourceRepository.findMenuByUserIdAndCode(userId, systemCode);
        if (lang == null) {
            lang = "";
        }
        for (ResourceEntity resourceEntity : list) {
            String key = resourceEntity.getLableKey();
            resourceEntity.getData().setName(resourceEntity.getName());
            if (StringUtils.isNotEmpty(key)) {
                MenuI18nEntity menuI18nEntity = menuI18nRepository.findByKeyAndLang(key, lang);
                if (menuI18nEntity == null && !lang.equals("")) {
                    menuI18nEntity = menuI18nRepository.findByKeyAndLang(key, "");
                }
                if (menuI18nEntity != null) {
                    String value = menuI18nEntity.getValue();
                    if (StringUtils.isNotEmpty(value)) {
                        resourceEntity.getData().setName(value);
                    }
                }
            }
        }
        List<TreeNode> menuData = TreeUtil.build(list);
        return menuData;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<TreeNode> getMenuByUsername(String userId, String lang) {
        List<ResourceEntity> list = resourceRepository.findMenuByUserIdAndCode(userId);
        if (lang == null) {
            lang = "";
        }
        for (ResourceEntity resourceEntity : list) {
            String key = resourceEntity.getLableKey();
            resourceEntity.getData().setName(resourceEntity.getName());
            if (StringUtils.isNotEmpty(key)) {
                MenuI18nEntity menuI18nEntity = menuI18nRepository.findByKeyAndLang(key, lang);
                if (menuI18nEntity == null && !lang.equals("")) {
                    menuI18nEntity = menuI18nRepository.findByKeyAndLang(key, "");
                }
                if (menuI18nEntity != null) {
                    String value = menuI18nEntity.getValue();
                    if (StringUtils.isNotEmpty(value)) {
                        resourceEntity.getData().setName(value);
                    }
                }
            }
        }

        List<TreeNode> menuData = TreeUtil.build(list);
        return menuData;
    }
}