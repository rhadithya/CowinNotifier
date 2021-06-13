package io.adi.cowinnotifier;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CowinNotifier {
    static Logger logger = LoggerFactory.getLogger(CowinNotifier.class);

    public static void main(String[] args) {
        logger.info("Starting CowinNotifier");

        CommandLine cmd = null;
        Options options = initCmdOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Error occured while parsing command line arguments.", e);
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        CowinNotifierService cowinNotifierService = new CowinNotifierService(cmd);
        cowinNotifierService.start();
    }

    private static Options initCmdOptions() {
        Options options = new Options();

        Option state = new Option("s", "state", true, "state");
        state.setRequired(true);
        options.addOption(state);

        Option district = new Option("d", "district", true, "district");
        district.setRequired(true);
        options.addOption(district);

        Option age = new Option("a", "age", true, "age");
        age.setRequired(true);
        options.addOption(age);

        Option searchAll = new Option("sa", "searchall", true, "search all centres without constraints");
        searchAll.setRequired(false);
        options.addOption(searchAll);

        Option pollingInterval = new Option("p", "pollinterval", true, "polling interval");
        pollingInterval.setRequired(false);
        options.addOption(pollingInterval);

        Option telegramToken = new Option("t", "token", true, "telegram token");
        telegramToken.setRequired(true);
        options.addOption(telegramToken);

        return options;
    }
}
