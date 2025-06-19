package com.kimsong.digital_banking.shared.log;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class RequestResponseLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Log incoming request
        logRequest((HttpServletRequest) servletRequest);

        filterChain.doFilter(servletRequest, servletResponse);

        // Log outgoing response
        logResponse((HttpServletResponse) servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void logRequest(HttpServletRequest request) {
        // Log request details
        log.info("request url:{}, method:{}, remote address:{}", request.getRequestURI(), request.getMethod(), request.getRemoteAddr());
    }

    private void logResponse(HttpServletResponse response) {
        // Log response details
        log.info("response status:{}", response.getStatus());
    }

}
