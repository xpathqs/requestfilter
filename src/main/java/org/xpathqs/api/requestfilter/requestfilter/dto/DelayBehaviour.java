package org.xpathqs.api.requestfilter.requestfilter.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConstantDelayBehaviour.class, name = "constant"),
        @JsonSubTypes.Type(value = RandomDelayBehaviour.class, name = "random"),
        @JsonSubTypes.Type(value = InternalDelayBehaviour.class, name = "internal")
})
public class DelayBehaviour {
    public final String type;

    public DelayBehaviour(String type) {
        this.type = type;
    }
}
