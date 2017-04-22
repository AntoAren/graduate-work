package by.bsu.zakharankou.restservices.controller.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class AuthenticationFilterUtils {

    private static final char REQUEST_URI_DELIMITER = '/';
    private static final int ONE_POSITION_SHIFT = 1;
    private static final int OCCURRENCE_INDEX = 0;

    private AuthenticationFilterUtils() {
    }

    public static boolean isRequestExcluded(final ServletRequest request, final List<Pattern> excludePatterns) {
        final HttpServletRequest servletRequest = (HttpServletRequest)request;
        final String contextPath = servletRequest.getContextPath();
        final String requestPath = servletRequest.getRequestURI().substring(contextPath.length());

        for (Pattern pattern : excludePatterns) {
            if (pattern.matcher(requestPath).find()) {
                return true;
            }
        }
        return false;
    }

    public static List<Pattern> prepareExcludePatterns(final List<String> excludePatterns) {
        if (excludePatterns == null || excludePatterns.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Pattern> patterns = new ArrayList<>(excludePatterns.size());
        for (String exclude : excludePatterns) {
            if (StringUtils.hasText(exclude)) {
                patterns.add(Pattern.compile(exclude));
            }
        }
        return patterns;
    }

    public static String getServiceName(final HttpServletRequest request) {
        final String contextPath = request.getContextPath();
        final String requestPath = request.getRequestURI().substring(contextPath.length());
        final int nameEndIndex = requestPath.indexOf(REQUEST_URI_DELIMITER, ONE_POSITION_SHIFT);
        return nameEndIndex >= OCCURRENCE_INDEX ? requestPath.substring(ONE_POSITION_SHIFT, nameEndIndex): requestPath.substring(ONE_POSITION_SHIFT);
    }
}
