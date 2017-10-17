package org.rootservices.pelican;

import java.util.List;
import java.util.Map;

public abstract class Worker {

    protected Subscribe subscribe;
    protected Long timeout;

    public Worker(Subscribe subscribe, Long timeout) {
        this.subscribe = subscribe;
        this.timeout = timeout;
    }

    public void work() {
        while(true) {
            List<Map<String, String>> msgs = subscribe.poll(timeout);
            handleMessage(msgs);
        }
    }

    abstract public void handleMessage(List<Map<String, String>> msgs);
}
