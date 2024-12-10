package org.xpathqs.api.requestfilter.requestfilter.dto;

public class ResponseConfig {
    final public Integer code;
    final public String body;

    public ResponseConfig(Integer code, String body) {
        this.code = code;
        this.body = body;
    }
}
