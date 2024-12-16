package org.xpathqs.api.requestfilter.requestfilter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xpathqs.api.requestfilter.requestfilter.component.DelayConfig;
import org.xpathqs.api.requestfilter.requestfilter.dto.AddDelayRequest;

@RestController
@RequestMapping("delay")
public class DelayController {
    @Autowired
    private DelayConfig config;

    private final Logger log = LoggerFactory.getLogger(DelayController.class);

    @PostMapping("/add")
    void addDelay(@RequestBody AddDelayRequest request) {
        log.info("add new delay $request");

        config.delays.removeIf(it -> it.hasSamePattern(request));
        config.delays.add(request);
    }

    @PostMapping("/disable-all")
    void disableAll() {
        log.info("disable all endpoints of the service");
        config.disableAll = true;
    }

    @PostMapping("/clear")
    void clear() {
        log.info("All delays was removed");
        config.delays.clear();
        config.disableAll = false;
    }

    @GetMapping("/dump")
    DelayConfig getAll() {
        return config;
    }
}
