package users;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeZones {

    private final List<TimeZoneEntry> entries;

    public TimeZones() {
        final LocalDateTime now = LocalDateTime.now();

        this.entries = ZoneId.getAvailableZoneIds().stream().map(zoneId -> {
            final ZoneId zone = ZoneId.of(zoneId);
            final ZoneOffset offset = zone.getRules().getOffset(now);
            return new TimeZoneEntry(zone, offset, offset + " " + zone);
        }).sorted().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public List<TimeZoneEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "TimeZones{" +
                "entries=" + entries +
                '}';
    }

    public static class TimeZoneEntry implements Comparable<TimeZoneEntry> {
        private final ZoneId zoneId;
        private final ZoneOffset zoneOffset;
        private final String display;

        public TimeZoneEntry(ZoneId zoneId, ZoneOffset zoneOffset, String display) {
            this.zoneId = zoneId;
            this.zoneOffset = zoneOffset;
            this.display = display;
        }

        public ZoneId getZoneId() {
            return zoneId;
        }

        public ZoneOffset getZoneOffset() {
            return zoneOffset;
        }

        public String getDisplay() {
            return display;
        }

        @Override
        public int compareTo(TimeZoneEntry o) {
            final int offsetCompare = -getZoneOffset().compareTo(o.getZoneOffset());
            return offsetCompare != 0 ? offsetCompare : getZoneId().getId().compareTo(o.getZoneId().getId());
        }

        @Override
        public String toString() {
            return "TimeZoneEntry{'" + display + "'}";
        }
    }
}
