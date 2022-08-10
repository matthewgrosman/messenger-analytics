package analytics;

import shared.MongoDBClient;

public class Analytics {
    /**
     * Generate conversation level analytics.
     * Want to type in a conversation name and see the following statistics:
     *  - Number of messages sent.
     *  - Number of messages sent each month (number and percentages).
     *  - Who has sent how many messages (number and percentages).
     *  - Most active hours (number and percentages).
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

    /**
     * Generate conversation level analytics.
     * Want to type in a conversation name and see the following statistics:
     *  - Number of messages sent.
     *  - Number of messages sent each month (number and percentages).
     *  - Who has sent how many messages (number and percentages).
     *  - Most active hours (number and percentages).
     *  - How many messages have been sent in each conversation (number and percentages).
     */
    public static void getAggregatedConversationData() {
        AggregatedConversationData conversationData = AggregatedConversationMongoDB.getConversationData();
        conversationData.printConversationData();
    }

    public static void main(String[] args) {
        MongoDBClient.getMongoDBConnection();

        getAggregatedConversationData();

        MongoDBClient.closeMongoDBConnection();
    }
}
