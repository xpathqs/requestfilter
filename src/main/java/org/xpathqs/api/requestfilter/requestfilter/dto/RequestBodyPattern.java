package org.xpathqs.api.requestfilter.requestfilter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RequestBodyPattern {
    public String contains;
    public String containsJsonPath;
    public String containsJsonValue;

    private final Logger log = LoggerFactory.getLogger(RequestBodyPattern.class);

    public Boolean isApplicable(String body) {
        if(contains != null && body.contains(contains)) {
            log.info("Apply contains for $url");
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBodyPattern that = (RequestBodyPattern) o;
        return Objects.equals(contains, that.contains)
                && Objects.equals(containsJsonPath, that.containsJsonPath)
                && Objects.equals(containsJsonValue, that.containsJsonValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contains, containsJsonPath, containsJsonValue);
    }
}
