package org.xpathqs.api.requestfilter.requestfilter.component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.xpathqs.api.requestfilter.requestfilter.dto.AddDelayRequest;
import org.xpathqs.api.requestfilter.requestfilter.util.CachedBodyHttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class DelayFilter implements Filter {
    @Autowired
    DelayConfig config;

    private final Logger log = LoggerFactory.getLogger(DelayFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CachedBodyHttpServletRequest request =
                new CachedBodyHttpServletRequest((HttpServletRequest) servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(config.disableAll) {
            setResponse(response, 500, "");
            return;
        }


        String host = StringUtils.substringAfter(request.getRequestURL().toString(),"//");
        String path = StringUtils.substringAfter(host,"/");

        log.debug("Filter for {}", path);

        String body  = request.getReader().lines().collect(Collectors.joining());
        log.debug("Body {}", body);
        try {
            AddDelayRequest afterProcess = config.process(path, body);
            if(afterProcess != null && afterProcess.response != null) {
                log.info("Response set in filter to {} code: {}", afterProcess.response.body, afterProcess.response.code);
                setResponse(response, afterProcess.response.code, afterProcess.response.body);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void setResponse(HttpServletResponse source, Integer code, String body) throws IOException {
        source.resetBuffer();
        source.setStatus(code);
        source.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        source.getOutputStream().print(body);
        source.flushBuffer();
    }
}
