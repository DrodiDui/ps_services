package com.kapitonau.authserver.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class AuthApplicationLogger extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logRequest(request);
            filterChain.doFilter(request, response);
        } finally {
            logResponse(response);
        }
    }

    private void logRequest(HttpServletRequest request) {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        logRequestHeader(requestWrapper);
        logRequestBody(requestWrapper);
    }

    private void logRequestBody(ContentCachingRequestWrapper request) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append("===============================Request body================================").append("\n");
        String[] body = request.getContentAsString().split("\n");
        for (String content : body) {
            builder.append(content).append("\n");
        }
        builder.append("==============================Request body end==============================").append("\n");
        log.info(builder.toString());
    }

    private void logRequestHeader(ContentCachingRequestWrapper request) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append("===============================START REQUEST===============================").append("\n");
        builder.append("Method: ").append(request.getMethod()).append("\n");
        builder.append("URI: ").append(request.getRequestURI()).append("\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            builder.append("==================================HEADERS==================================").append("\n");
        }
        while (true) {
            if (headerNames != null && headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                Enumeration<String> headerValues = request.getHeaders(headerName);
                String headerValue = "";
                while (headerValues.hasMoreElements()) {
                    headerValue += headerValues.nextElement() + " ";
                }
                builder.append(headerName).append(": ").append(headerValue).append("\n");
            } else {
                builder.append("===========================================================================").append("\n");
                break;
            }
        }
        log.info(builder.toString());
    }

    private void logResponse(HttpServletResponse response) {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
    }
}
