package org.rootservices.pelican;

import java.util.Map;

public abstract class Worker {

    private Subscribe subscribe;
    private Long timeout;

    Worker(Subscribe subscribe, Long timeout) {
        this.subscribe = subscribe;
        this.timeout = timeout;
    }

    public void work() {
        while(true) {
            Map<String, String> message = subscribe.poll(timeout);
            handleMessage(message);
        }
    }

    abstract public void handleMessage(Map<String, String> message);
}
