package org.xpathqs.api.requestfilter.requestfilter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddDelayRequest {
    public final RequestPattern pattern;
    public final ResponseConfig response;
    public final DelayBehaviour delay;

    private final Logger log = LoggerFactory.getLogger(AddDelayRequest.class);

    public AddDelayRequest(RequestPattern pattern, ResponseConfig response, DelayBehaviour delay) {
        this.pattern = pattern;
        this.response = response;
        this.delay = delay;
    }

    public Boolean isApplicable(String url) {
        if (url.equals(pattern.equals)) {
            log.info("Apply equals for $url");
            return true;
        }
        if (pattern.startsWith != null && url.startsWith(pattern.startsWith)) {
            log.info("Apply startsWith for $url");
            return true;
        }
        if (pattern.endsWith != null && url.endsWith(pattern.endsWith)) {
            log.info("Apply endsWith for $url");
            return true;
        }
        if (pattern.contains != null && url.contains(pattern.contains)) {
            log.info("Apply contains for $url");
            return true;
        }

        log.info("No filter for $url");

        return false;
    }
}
