package io.adi.cowinnotifier;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
    Store some basic info needed.
    This needs to be moved to DB.
    This limits to one-user one-region.
 */
@Data
public class DataStore {
    // whom to send. based on chatId.
    private List<Long> toNotifyChatIds;

    // dont send message for same vaccine session again.
    private Set<String> notifiedVaccineSessions;

    DataStore() {
        this.toNotifyChatIds = new ArrayList<>();
        this.notifiedVaccineSessions = new HashSet<>();
    }
}
