package analytics;

import java.util.HashMap;

public class AggregatedConversationData implements ConversationData {
    public long numberOfMessages;
    public HashMap<String, Integer> messagesPerConversation;

    /**
     * Constructor for the AggregatedConversationData class. We need this to ensure
     * that the messagesPerSender HashMap gets initialized since this variable
     * is not defined in the ConversationData interface.
     */
    public AggregatedConversationData() {
        messagesPerConversation = new HashMap<>();
    }

    /**
     * Prints out the conversation data.
     *
     * NOTE: Yes this is janky and ugly, but the final version will be presented in a GUI or
     * some other clean way of displaying data. This is mainly just here to test and validate that
     * the code is working.
     */
    public void printConversationData() {
        System.out.println("Total number of messages sent: " + numberOfMessages);

        System.out.println();

        for (String key : messagesPerPerson.keySet()) {
            System.out.printf("Name: " + key
                    + ", Number of Messages: " + messagesPerPerson.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerPerson.get(key)));
        }

        System.out.println();

        for (String key : messagesPerConversation.keySet()) {
            System.out.printf("Conversation Name: " + key
                    + ", Number of Messages: " + messagesPerConversation.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerConversation.get(key)));
        }

        System.out.println();

        for (String key : messagesPerMonth.keySet()) {
            System.out.printf("Date: " + key
                    + ", Number of Messages: " + messagesPerMonth.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerMonth.get(key)));
        }

        System.out.println();

        for (String key : messagesPerWeekday.keySet()) {
            System.out.printf("Weekday: " + key
                    + ", Number of Messages: " + messagesPerWeekday.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerWeekday.get(key)));
        }

        System.out.println();

        for (String key : messagesPerHour.keySet()) {
            System.out.printf("Hour: " + key
                    + ", Number of Messages: " + messagesPerHour.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerHour.get(key)));
        }
    }
}
