package urmat.dev.megatask.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();
        chain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - start;

        logRequest(wrappedRequest);
        logResponse(wrappedResponse, duration);

        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = getContent(request.getContentAsByteArray());
        log.info("Request : {} {} | Body: {}", request.getMethod(), request.getRequestURI(), body);
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        String body = getContent(response.getContentAsByteArray());
        log.info("Request: {} | Duration: {}ms | Body: {}", response.getStatus(), duration, body);
    }

    private String getContent(byte[] content) {
        if (content == null || content.length == 0) return "Empty";
        String str = new String(content, StandardCharsets.UTF_8).trim();
        return str.length() > 300 ? str.substring(0, 300) + "..." : str;
    }
}
