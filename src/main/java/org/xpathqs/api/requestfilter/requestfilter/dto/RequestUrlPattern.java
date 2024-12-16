package org.xpathqs.api.requestfilter.requestfilter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RequestUrlPattern {
    public String equals;
    public String startsWith;
    public String endsWith;
    public String contains;

    private final Logger log = LoggerFactory.getLogger(RequestUrlPattern.class);

    public Boolean isApplicable(String url) {

        if (equals != null && url.equals(equals)) {
            log.info("Apply equals for $url");
            return true;
        }
        if (startsWith != null && url.startsWith(startsWith)) {
            log.info("Apply startsWith for $url");
            return true;
        }
        if (endsWith != null && url.endsWith(endsWith)) {
            log.info("Apply endsWith for $url");
            return true;
        }
        if (contains != null && url.contains(contains)) {
            log.info("Apply contains for $url");
            return true;
        }

        log.info("No filter for $url");

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUrlPattern that = (RequestUrlPattern) o;
        return Objects.equals(equals, that.equals)
                && Objects.equals(startsWith, that.startsWith)
                && Objects.equals(endsWith, that.endsWith)
                && Objects.equals(contains, that.contains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equals, startsWith, endsWith, contains);
    }
}