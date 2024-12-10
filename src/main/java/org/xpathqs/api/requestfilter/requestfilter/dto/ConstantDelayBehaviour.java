package org.xpathqs.api.requestfilter.requestfilter.dto;

public class ConstantDelayBehaviour extends DelayBehaviour {
    public final Long ts;

    public ConstantDelayBehaviour(Long ts) {
        super("constant");
        this.ts = ts;
    }
}
