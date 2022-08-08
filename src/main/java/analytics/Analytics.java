package analytics;

import shared.MongoDBClient;

import java.util.HashMap;

public class Analytics {
    /**
     * Generate conversation level analytics.
     * Want to type in a conversation name and see the following statistics:
     *      - Number of messages sent.
     *      - Number of messages sent each month (number and percentages).
     *      - Who has sent how many messages (number and percentages).
     *      - Most active hours (number and percentages).
     *
     * @param conversationName  A String representing the name of the conversation user wants analytics for.
     */
    public static void getIndividualConversationData(String conversationName) {
        // First, check if the conversation name entered is a valid conversation.
        if (IndividualConversationMongoDB.isConversationValid(conversationName)) {
            // Get number of total messages sent
            long numberOfMessages = IndividualConversationMongoDB.getNumberOfMessages(conversationName);
            System.out.println("Total number of messages sent: " + numberOfMessages);

            System.out.println();

            // Get how many messages each person has sent (as a number and percentage)
            HashMap<String, Integer> messagesPerPerson = IndividualConversationMongoDB.getNumberOfMessagesPerPerson(conversationName);
            for (String key : messagesPerPerson.keySet()) {
                System.out.printf("Name: " + key
                        + ", Number of Messages: " + messagesPerPerson.get(key));
                System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerPerson.get(key)));
            }

            System.out.println();

            // Get how many messages have been sent each month (as a number and percentage)
            HashMap<String, Integer> messagePerMonth = IndividualConversationMongoDB.getNumberOfMessagesPerMonth(conversationName);
            for (String key : messagePerMonth.keySet()) {
                System.out.printf("Date: " + key
                        + ", Number of Messages: " + messagePerMonth.get(key));
                System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagePerMonth.get(key)));
            }

            System.out.println();

            // Get the most active days (as a number and percentage)
            HashMap<String, Integer> messagesPerWeekday = IndividualConversationMongoDB.getNumberOfMessagesPerWeekday(conversationName);
            for (String key : messagesPerWeekday.keySet()) {
                System.out.printf("Weekday: " + key
                        + ", Number of Messages: " + messagesPerWeekday.get(key));
                System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerWeekday.get(key)));
            }

            System.out.println();

            // Get the most active hours (as a number and percentage)
            HashMap<String, Integer> messagesPerHour = IndividualConversationMongoDB.getNumberOfMessagesPerHour(conversationName);
            for (String key : messagesPerHour.keySet()) {
                System.out.printf("Hour: " + key
                        + ", Number of Messages: " + messagesPerHour.get(key));
                System.out.printf(" (%.2f%%)%n", getProportionAsPercentage(numberOfMessages, messagesPerHour.get(key)));
            }
        }
    }

    private static double getProportionAsPercentage(long totalNumberOfMessages, int currentNumberOfMessages) {
        // We need to convert one of these numbers to a decimal type in order to not just get 0 when dividing the
        // two numbers
        return ((currentNumberOfMessages*1.0) / totalNumberOfMessages) * 100;
    }

    // Generate analytics about all conversations over a given time range.
    // We want to see:
    //      - What conversations (group chat and individual) have exchanged the most messages (number and percentages).
    //      - Most active hours (number and percentages).
    //      - Who has sent the most messages across all conversations (number and percentages).

    public static void main(String[] args) {
        MongoDBClient.getMongoDBConnection();

        getIndividualConversationData("BOMB-FIRE");

        MongoDBClient.closeMongoDBConnection();
    }
}
