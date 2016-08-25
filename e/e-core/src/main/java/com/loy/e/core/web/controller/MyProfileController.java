package com.loy.e.core.web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loy.app.common.service.MyProfileService;
import com.loy.app.common.vo.UserDetail;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.security.service.UserSessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "**/profile", method = { RequestMethod.POST, RequestMethod.GET })

@Api(value = "个人相关信息操作", description = "个人相关信息操作")
public class MyProfileController {
    @Autowired
    MyProfileService myProfileService;
    @Autowired
    UserSessionService userSessionService;

    @ControllerLogExeTime(description = "上传个人图片")
    @RequestMapping(value = "/upload", method = { RequestMethod.POST })

    @ApiOperation(value = "上传个人图片", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "avatar", value = "图片", paramType = "form", required = true, dataType = "file")
    })

    public void upload(@RequestParam MultipartFile avatar) throws IOException {
        byte[] photoData = avatar.getBytes();
        myProfileService.upload(userSessionService.getSessionUser().getUsername(), photoData);
    }

    @ControllerLogExeTime(description = "获取个人图片", log = false)
    @RequestMapping(value = "/photo", method = { RequestMethod.GET })

    @ApiOperation(value = "获取用户图片", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", paramType = "form", required = true, dataType = "string")
    })
    public void photo(String id, HttpServletResponse response) throws IOException {
        byte[] photoData = myProfileService.photo(id);
        OutputStream out = response.getOutputStream();
        if (photoData != null) {
            out.write(photoData);
        }
        out.flush();
        out.close();
    }

    @ControllerLogExeTime(description = "编辑个人资料", log = false)
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })

    @ApiOperation(value = "获取个人信息", httpMethod = "GET")

    public UserDetail get() {
        UserDetail user = myProfileService.get(userSessionService.getSessionUser().getUsername());
        return user;
    }

    @ControllerLogExeTime(description = "修改个人资料")
    @RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })

    @ApiOperation(value = "修改个人资料", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "form", dataType = "string"),
    })

    public void update(@ApiIgnore UserDetail user) {
        String username = userSessionService.getSessionUser().getUsername();
        user.setUsername(username);
        myProfileService.update(user);
    }

    @ControllerLogExeTime(description = "修改个人密码")
    @RequestMapping(value = "/password", method = { RequestMethod.POST, RequestMethod.PUT })

    @ApiOperation(value = "修改个人密码", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "form", required = true, dataType = "string")
    })

    public void updatePassword(String oldPassword, String newPassword) {
        String username = userSessionService.getSessionUser().getUsername();
        myProfileService.updatePassword(username, oldPassword, newPassword);

    }
}
