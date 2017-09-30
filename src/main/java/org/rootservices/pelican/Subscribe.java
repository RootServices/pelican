package org.rootservices.pelican;

import java.util.List;
import java.util.Map;

public interface Subscribe {
    List<Map<String, String>> poll(long timeout);
    void processed();
}
