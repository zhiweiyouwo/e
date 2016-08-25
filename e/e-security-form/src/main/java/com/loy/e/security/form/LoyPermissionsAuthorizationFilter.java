package com.loy.e.security.form;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loy.e.common.util.ExceptionUtil;
import com.loy.e.common.vo.ErrorResponseData;
import com.loy.e.security.service.LoyLogService;
import com.loy.e.security.service.SystemKeyService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Autowired(required = false)
    private LoyLogService loyLogService;
    @Autowired(required = false)
    SystemKeyService systemKeyService;

    @Autowired(required = false)
    public MessageSource messageSource;

    public LoyPermissionsAuthorizationFilter() {

    }

    public LoyPermissionsAuthorizationFilter(LoyLogService loyLogService,
            SystemKeyService systemKeyService,
            MessageSource messageSource) {
        this.loyLogService = loyLogService;
        this.systemKeyService = systemKeyService;
        this.messageSource = messageSource;
    }

    @SuppressWarnings("deprecation")
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws IOException {

        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
                        .createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
                ErrorResponseData error = new ErrorResponseData();
                error.setErrorCode("not_permission");
                String msg = messageSource.getMessage("no_permission", null,
                        LocaleContextHolder.getLocale());
                error.setMsg(msg);
                jsonGenerator.writeObject(error);

            } catch (IOException e) {
                String stackTraceMsg = ExceptionUtil.exceptionStackTrace(e);
                loyLogService.exception(systemKeyService.getSystemCode(), e.getClass().getName(),
                        stackTraceMsg);
                throw e;
            }
        }
        return false;
    }

}