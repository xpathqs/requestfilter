package org.xpathqs.api.requestfilter.requestfilter.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xpathqs.api.requestfilter.requestfilter.dto.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Component
public class DelayConfig {
    public final ArrayList<AddDelayRequest> delays = new ArrayList<>();
    public Boolean disableAll = false;

    private final Logger log = LoggerFactory.getLogger(DelayConfig.class);

    AddDelayRequest process(String url, String body) throws InterruptedException {
        Optional<AddDelayRequest> request = delays
            .stream()
            .filter((it) -> it.isApplicable(url, body))
            .findFirst();

        if(request.isPresent()) {
            log.debug("Filter applied for the {}", url);
            DelayBehaviour delay = request.get().delay;
            if(delay instanceof ConstantDelayBehaviour) {
                Long sleepFor = ((ConstantDelayBehaviour) delay).ts;
                log.info("Sleep for {}", sleepFor);
                Thread.sleep(sleepFor);
            }
            else if(delay instanceof RandomDelayBehaviour) {
                long min = ((RandomDelayBehaviour) delay).minTs;
                long max = ((RandomDelayBehaviour) delay).maxTs;
                long sleepFor = RandomGenerator.getDefault().nextLong(min, max);
                log.info("Sleep for {}", sleepFor);
                Thread.sleep(sleepFor);
            }
            else if(delay instanceof InternalDelayBehaviour) {
                log.info("Sleep for internal period");
                Thread.sleep(Long.MAX_VALUE);
            } else {
                log.info("Ignore sleep");
            }
            return request.get();
        }
        return null;
    }
}
