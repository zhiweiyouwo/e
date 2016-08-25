package com.loy.cas;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class InitialFlowSetupAction extends AbstractAction {

    @NotNull
    private CookieRetrievingCookieGenerator warnCookieGenerator;

    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    @NotNull
    @Size(min = 1)
    private List<ArgumentExtractor> argumentExtractors;
    private boolean pathPopulated = false;

    protected Event doExecute(RequestContext context) throws Exception {
        HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        if (!this.pathPopulated) {
            String contextPath = context.getExternalContext().getContextPath();
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath + "/" : "/";
            this.logger.info("Setting path for cookies to: " + cookiePath);

            this.warnCookieGenerator.setCookiePath(cookiePath);
            this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
            this.pathPopulated = true;
        }

        context.getFlowScope().put("ticketGrantingTicketId",
                this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request));

        context.getFlowScope().put("warnCookieValue",
                Boolean.valueOf(this.warnCookieGenerator.retrieveCookieValue(request)));

        Service service = WebUtils.getService(this.argumentExtractors, context);

        if ((service != null) && (this.logger.isDebugEnabled())) {
            this.logger.debug("Placing service in FlowScope: " + service.getId());
        }

        context.getFlowScope().put("service", service);

        return result("success");
    }

    public void setTicketGrantingTicketCookieGenerator(
            CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }

    public void setWarnCookieGenerator(CookieRetrievingCookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    public void setArgumentExtractors(List<ArgumentExtractor> argumentExtractors) {
        this.argumentExtractors = argumentExtractors;
    }

    public boolean isPathPopulated() {
        return pathPopulated;
    }

    public void setPathPopulated(boolean pathPopulated) {
        this.pathPopulated = pathPopulated;
    }

}