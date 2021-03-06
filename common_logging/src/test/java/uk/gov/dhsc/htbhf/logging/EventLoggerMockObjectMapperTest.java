package uk.gov.dhsc.htbhf.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.slf4j.event.Level.WARN;
import static uk.gov.dhsc.htbhf.logging.TestConstants.EVENT;

@ExtendWith(MockitoExtension.class)
class EventLoggerMockObjectMapperTest extends AbstractLoggingTest {

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private EventLogger eventLogger;

    @Test
    void shouldStillLogToStringWhenJsonSerialisationFails() throws JsonProcessingException {
        //Given
        given(mockObjectMapper.writeValueAsString(any())).willThrow(new JsonMappingException(null, "Test exception"));
        //When
        eventLogger.logEvent(EVENT);
        //Then
        assertSingleLogMessageHasText("Unable to write event as JSON, reverting to standard toString(): " + EVENT.toString(), WARN);
    }

}
