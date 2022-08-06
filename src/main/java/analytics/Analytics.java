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
                System.out.println("Name: " + key + ", Number of Messages: " + messagesPerPerson.get(key));
            }

            System.out.println();

            // Get how many messages have been sent each month (as a number and percentage)
            HashMap<String, Integer> messagePerMonth = IndividualConversationMongoDB.getNumberOfMessagesPerMonth(conversationName);
            for (String key : messagePerMonth.keySet()) {
                System.out.println("Month: " + key + ", Number of Messages: " + messagePerMonth.get(key));
            }

            // Get the most active days and hours (as a number and percentage)
        }
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
