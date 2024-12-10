package org.xpathqs.api.requestfilter.requestfilter.dto;

public class RandomDelayBehaviour extends DelayBehaviour {
    public final Long minTs, maxTs;

    public RandomDelayBehaviour(Long minTs, Long maxTs) {
        super("random");
        this.minTs = minTs;
        this.maxTs = maxTs;
    }
}
