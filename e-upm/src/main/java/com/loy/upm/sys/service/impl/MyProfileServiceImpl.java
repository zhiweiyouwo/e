package com.loy.upm.sys.service.impl;

import java.io.IOException;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loy.app.common.service.MyProfileService;
import com.loy.app.common.vo.UserDetail;
import com.loy.e.common.util.Assert;
import com.loy.upm.personnel.domain.entity.EmployeeEntity;
import com.loy.upm.personnel.repository.EmployeeRepository;
import com.loy.upm.sys.domain.entity.UserEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@Transactional
@Service(value = "myProfileService")
public class MyProfileServiceImpl implements MyProfileService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordService passwordService;

    public void upload(String username, byte[] avatar) throws IOException {
        EmployeeEntity employeeEntity = employeeRepository.findByUsername(username);
        employeeEntity.setPhotoData(avatar);
        employeeRepository.save(employeeEntity);

    }

    public byte[] photo(String id) throws IOException {
        EmployeeEntity employeeEntity = employeeRepository.get(id);
        if (employeeEntity != null) {
            return employeeEntity.getPhotoData();
        }
        return null;
    }

    public UserDetail get(String username) {
        EmployeeEntity user = employeeRepository.findByUsername(username);
        user.buildRoleIdAnadName();
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(user, userDetail);
        userDetail.setId(user.getId());
        userDetail.setPhoto(user.getPhotoData() != null ? true : false);
        return userDetail;
    }

    public void update(UserDetail user) {
        EmployeeEntity employeeEntity = employeeRepository.findByUsername(user.getUsername());
        employeeEntity.setName(user.getName());
        employeeEntity.setEmail(user.getEmail());
        employeeEntity.setPhone(user.getPhone());
        employeeRepository.save(employeeEntity);

    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = employeeRepository.findByUsername(username);
        String password = user.getPassword();
        oldPassword = passwordService.encryptPassword(oldPassword);
        if (password.equals(oldPassword)) {
            String enPassword = passwordService.encryptPassword(newPassword);
            user.setPassword(enPassword);
        } else {
            Assert.throwException("sys.user.old_password");
        }

    }
}
