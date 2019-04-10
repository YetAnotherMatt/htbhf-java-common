package uk.gov.dhsc.htbhf.logging;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
public class Event {
    private EventType eventType;
    private LocalDateTime timestamp;
    private Map<String, Object> eventMetadata;

    //This is here to make sure that the field "eventMetadata" isn't shown in the logs
    @JsonAnyGetter
    public Map<String, Object> getEventMetadata() {
        Map<String, Object> metadata = new TreeMap<>();
        if (eventMetadata != null) {
            metadata.putAll(eventMetadata);
        }
        return metadata;
    }
}