package network.pyth.staking.types;

import java.util.Date;
import java.util.List;

public class UnlockSchedule {
    private final UnlockType type;
    private final List<ScheduleEntry> schedule;

    public UnlockSchedule(UnlockType type, List<ScheduleEntry> schedule) {
        this.type = type;
        this.schedule = schedule;
    }

    public UnlockType getType() {
        return type;
    }

    public List<ScheduleEntry> getSchedule() {
        return schedule;
    }

    public static class ScheduleEntry {
        private final Date date;
        private final long amount;

        public ScheduleEntry(Date date, long amount) {
            this.date = date;
            this.amount = amount;
        }

        public Date getDate() {
            return date;
        }

        public long getAmount() {
            return amount;
        }
    }

    public enum UnlockType {
        FULLY_UNLOCKED,
        PERIODIC_UNLOCKING_AFTER_LISTING,
        PERIODIC_UNLOCKING
    }
} 