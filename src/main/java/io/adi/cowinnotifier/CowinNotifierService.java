package io.adi.cowinnotifier;
import io.adi.cowinnotifier.models.VaccineSearchResult;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CowinNotifierService {
    static Logger logger = LoggerFactory.getLogger(CowinNotifierService.class);

    private DataStore dataStore;
    private CowinRestClient cowinClient;
    private CowinTelegramClient telegramClient;

    private String state;
    private String district;
    private int age;
    private boolean searchAll;
    private int pollingInterval;
    private String token;

    CowinNotifierService(CommandLine cmd) {
        init(cmd);
        dataStore = new DataStore();
        cowinClient = new CowinRestClient(dataStore);
        telegramClient = new CowinTelegramClient(dataStore, token);
    }

    private void init(CommandLine cmd) {
        state = cmd.getOptionValue("s");
        district = cmd.getOptionValue("d");
        token = cmd.getOptionValue("t");
        age = 45;
        searchAll = false;
        pollingInterval = 5000;

        try {
            searchAll = Boolean.parseBoolean(cmd.getOptionValue("sa", "false"));
            pollingInterval = Integer.parseInt(cmd.getOptionValue("p", "5000"));
            age = Integer.parseInt(cmd.getOptionValue("a"));
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid Values. Proceeding with default values");
        }

        logger.info("Init CowinNotifierService with: state={}, district={} token=*** age={} searchAll={} pollingInterval={}"
        , state, district, age, searchAll, pollingInterval);
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Date now = new Date();
            logger.info("Polling @ {}", now);
            List<VaccineSearchResult> searchResults = cowinClient.checkAvailabilityByStateAndDistrict(state, district, now, age, searchAll);
            String message = messageToNotify(searchResults);
            if (message.length() > 0) {
                telegramClient.sendMessage(message);
            }
        }, 0, pollingInterval, TimeUnit.MILLISECONDS);
    }

    private String messageToNotify(List<VaccineSearchResult> searchResults) {
        StringBuilder messageBuilder = new StringBuilder();
        for(VaccineSearchResult searchResult: searchResults) {
            String vaccineSessionId = searchResult.getSession().getSessionId();
            if(!dataStore.getNotifiedVaccineSessions().contains(vaccineSessionId)) {
                messageBuilder.append(searchResult);
                dataStore.getNotifiedVaccineSessions().add(vaccineSessionId);
            }
        }
        return messageBuilder.toString();
    }
}
