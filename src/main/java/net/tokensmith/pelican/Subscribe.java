package net.tokensmith.pelican;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface Subscribe {
    List<Map<String, String>> poll(Duration timeout);
    void processed();
}
