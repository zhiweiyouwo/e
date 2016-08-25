package com.loy.e.security.form;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loy.e.common.Constants;
import com.loy.e.common.util.Assert;
import com.loy.e.common.vo.LoginSuccessResponse;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.vo.User;

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
@Controller
@Api(value = "登录", description = "登录")
public class LoginController {
    @Autowired
    private SecurityUserService securityUserService;

    @Value(value = "${server.session.timeout}")
    Integer serverSessionTimeout = 30;
    @Value(value = "${e.form.successUrl}")
    String successUrl = "home.html";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiIgnore
    public String login() {
        return "redirect:index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiIgnore
    public String loginForm() {
        return "redirect:index.html";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = { RequestMethod.POST })

    @ApiOperation(value = "登入", notes = "登入", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", dataType = "string")

    })
    public LoginSuccessResponse login(String username,
            String password) {

        AuthenticationToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            Assert.throwException("user_password_error");
        } catch (IncorrectCredentialsException e) {
            Assert.throwException("user_password_error");
        }

        SessionUser sessionUser = new SessionUser();
        User user = securityUserService.findByUsername(username);
        sessionUser.setUsername(username);
        sessionUser.setName(user.getName());
        sessionUser.setId(user.getId());
        sessionUser.setPhoto(user.getPhoto());
        subject.getSession().setTimeout(serverSessionTimeout * 60 * 1000);
        subject.getSession().setAttribute(Constants.SESSION_KEY, sessionUser);
        LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
        loginSuccessResponse.setHome(successUrl);

        return loginSuccessResponse;
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    @ApiIgnore
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:index.html";
    }

}
