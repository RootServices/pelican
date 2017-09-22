package org.rootservices.pelican;

import java.util.Map;

public interface Subscribe {
    Map<String, String> poll(long timeout);
}
