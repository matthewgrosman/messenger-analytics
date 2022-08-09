package analytics;

import shared.MongoDBClient;

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
        // Check if the conversation name entered is a valid conversation before grabbing data
        if (SingleConversationMongoDB.isConversationValid(conversationName)) {
            SingleConversationData conversationData = SingleConversationMongoDB.getConversationData(conversationName);
            conversationData.printConversationData();
        }
    }

    // Generate analytics about all conversations over a given time range.
    // We want to see:
    //      - Total number of messages across all conversations
    //      - What conversations (group chat and individual) have exchanged the most messages (number and percentages).
    //      - Most active months (number and percentage).
    //      - Most active days (number and percentage)
    //      - Most active hours (number and percentages).
    //      - Who has sent the most messages across all conversations (number and percentages).

    // Add feature in the future to give input of person and find out how many messages they've sent you total

    public static void getAggregatedConversationData() {
        AggregatedConversationData conversationData = AggregatedConversationMongoDB.getConversationData();
        conversationData.printConversationData();
    }

    public static void main(String[] args) {
        MongoDBClient.getMongoDBConnection();

        getIndividualConversationData("BOMB-FIRE");

        MongoDBClient.closeMongoDBConnection();
    }
}
