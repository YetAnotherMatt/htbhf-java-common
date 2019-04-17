package uk.gov.dhsc.htbhf.logging.event;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.logging.event.FailureEvent.FAILED_EVENT_KEY;
import static uk.gov.dhsc.htbhf.logging.event.FailureEvent.FAILURE_DESCRIPTION_KEY;

class FailureEventTest {

    @Test
    void shouldIncludeDetailsOfFailedEvent() {
        // given
        ApplicationStartedEvent event = ApplicationStartedEvent.builder()
                .instanceIndex("1")
                .applicationId("foo")
                .applicationVersion("1.0.0")
                .build();

        // when
        FailureEvent failedEvent = FailureEvent.builder()
                .failedEvent(event)
                .failureDescription("myExceptionMessage")
                .build();

        // then
        assertThat(failedEvent.getEventType()).isEqualTo(CommonEventType.FAILURE);
        assertThat(failedEvent.getTimestamp()).isEqualTo(event.getTimestamp());
        Map<String, Object> metadata = failedEvent.getEventMetadata();
        assertThat(metadata).containsAllEntriesOf(event.getEventMetadata());
        assertThat(metadata.get(FAILED_EVENT_KEY)).isEqualTo(event.getEventType());
        assertThat(metadata.get(FAILURE_DESCRIPTION_KEY)).isEqualTo("myExceptionMessage");
    }

    @Test
    void shouldHandleNullEvents() {
        // when
        FailureEvent failedEvent = FailureEvent.builder()
                .failureDescription("something went wrong!")
                .build();

        // then
        assertThat(failedEvent.getEventType()).isEqualTo(CommonEventType.FAILURE);
        assertThat(failedEvent.getTimestamp()).isNotNull();
        Map<String, Object> metadata = failedEvent.getEventMetadata();
        assertThat(metadata).isNotNull();
        assertThat(metadata.get(FAILED_EVENT_KEY)).isNull();
        assertThat(metadata.get(FAILURE_DESCRIPTION_KEY)).isEqualTo("something went wrong!");

    }
}