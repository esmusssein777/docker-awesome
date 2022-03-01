package com.ligz.docker.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.ligz.docker.log.MDCConstants.REQUEST_ID;
import static com.ligz.docker.log.MDCConstants.REQUEST_URI;


public class LogRequestFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        MDC.clear();

        String requestId = request.getHeader(REQUEST_ID);
        MDC.put(REQUEST_ID, StringUtils.isNotBlank(requestId) ? requestId : UUID.randomUUID().toString());
        MDC.put(REQUEST_URI, request.getRequestURI());

        chain.doFilter(req, res);
    }
}
