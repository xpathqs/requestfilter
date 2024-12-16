package org.xpathqs.api.requestfilter.requestfilter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class AddDelayRequest {
    public final RequestUrlPattern urlPattern;
    public final RequestBodyPattern bodyPattern;
    public final ResponseConfig response;
    public final DelayBehaviour delay;

    private final Logger log = LoggerFactory.getLogger(AddDelayRequest.class);

    public AddDelayRequest(
            RequestUrlPattern urlPattern,
            RequestBodyPattern bodyPattern,
            ResponseConfig response,
            DelayBehaviour delay
    ) {
        this.urlPattern = urlPattern;
        this.response = response;
        this.delay = delay;
        this.bodyPattern = bodyPattern;
    }

    public Boolean isApplicable(String url, String body) {
        if(urlPattern != null) {
            if(urlPattern.isApplicable(url)) {
                if(bodyPattern != null) {
                    return bodyPattern.isApplicable(body);
                }
                return true;
            }
        } else {
            if(bodyPattern != null) {
                return bodyPattern.isApplicable(body);
            }
        }

        log.debug("No filter for {}", url);

        return false;
    }

    public boolean hasSamePattern(AddDelayRequest request) {
        return Objects.equals(urlPattern, request.urlPattern)
                && Objects.equals(bodyPattern, request.bodyPattern);
    }
}
