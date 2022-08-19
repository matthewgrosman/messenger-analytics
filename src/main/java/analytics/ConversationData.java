package analytics;

import java.util.HashMap;

public class ConversationData {
    public long numberOfMessages;
    public HashMap<String, Integer> messagesPerConversation;
    HashMap<String, Integer> messagesPerPerson;
    HashMap<String, Integer> messagesPerMonth;
    HashMap<String, Integer> messagesPerWeekday;
    HashMap<String, Integer> messagesPerHour;

    /**
     * Constructor for the ConversationData class. Initialize all of the HashMaps so that
     * they can be used in the ConversationDataMongoDB class to store analytics data.
     */
    public ConversationData() {
        messagesPerConversation = new HashMap<>();
        messagesPerPerson = new HashMap<>();
        messagesPerMonth = new HashMap<>();
        messagesPerWeekday = new HashMap<>();
        messagesPerHour = new HashMap<>();
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

        for (String key : messagesPerConversation.keySet()) {
            System.out.printf("Conversation Name: " + key
                    + ", Number of Messages: " + messagesPerConversation.get(key));
            System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerConversation.get(key)));
        }
        System.out.println();

        for (String key : messagesPerPerson.keySet()) {
            System.out.printf("Name: " + key
                    + ", Number of Messages: " + messagesPerPerson.get(key));
            System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerPerson.get(key)));
        }

        System.out.println();

        for (String key : messagesPerMonth.keySet()) {
            System.out.printf("Date: " + key
                    + ", Number of Messages: " + messagesPerMonth.get(key));
            System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerMonth.get(key)));
        }

        System.out.println();

        for (String key : messagesPerWeekday.keySet()) {
            System.out.printf("Weekday: " + key
                    + ", Number of Messages: " + messagesPerWeekday.get(key));
            System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerWeekday.get(key)));
        }

        System.out.println();

        for (String key : messagesPerHour.keySet()) {
            System.out.printf("Hour: " + key
                    + ", Number of Messages: " + messagesPerHour.get(key));
            System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerHour.get(key)));
        }
    }

    /**
     * Gets current number of messages as a percent of the total number of messages.
     *
     * @param totalNumberOfMessages     A long representing the total number of messages sent in the conversation.
     * @param currentNumberOfMessages   A long representing the current number of messages we want to find a
     *                                  percentage for.
     * @return                          A double representing the proportion of current messages to total
     *                                  messages as a percentage.
     */
    private static double getProportionAsPercentage(long totalNumberOfMessages, int currentNumberOfMessages) {
        // We need to convert one of these numbers to a decimal type in order to not
        // just get 0 when dividing the two numbers.
        return ((currentNumberOfMessages*1.0) / totalNumberOfMessages) * 100;
    }
}
