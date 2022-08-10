package analytics;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import shared.MongoDBClient;

public class AggregatedConversationMongoDB {
    /**
     * Gets all relevant conversation data for the current conversation. This includes getting data on
     * conversation message counts, message senders, the months that messages were sent, the days of
     * the week that messages were sent, and the hours that messages were sent. This is all returned
     * neatly in an AggregatedConversationData object that has public class variables for all relevant
     * data collected in this function.
     *
     * @return  An AggregatedConversationData object that contains all relevant
     *          analytics data collected in this function.
     */
    public static AggregatedConversationData getConversationData() {
        AggregatedConversationData conversationData = new AggregatedConversationData();

        // Grab the total number of documents.
        conversationData.numberOfMessages = MongoDBClient.messagesCollection.countDocuments();

        // Iterate through all conversation names and record number of messages in each conversation.
        // for (Conversation c : conversation) {
        //      BasicDBObject query = new BasicDBObject();
        //      query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        //
        //      long count = MongoDBClient.messagesCollection.countDocuments(query);
        //              conversationData.messagesPerConversation.put(date, conversationData.messagesPerConversation.getOrDefault(date, 0) + 1);

        // Iterate through the messages and grab data we need from each message. This includes getting the
        // message sender, the month that message was sent, the day of week that the message was sent, and
        // the hour that the message was sent.
        FindIterable<Document> conversationMessages = MongoDBClient.messagesCollection.find();
        for (Document message : conversationMessages) {
            AnalyticsUtil.updateMessagesPerPerson(message, conversationData);
            AnalyticsUtil.updateMessagesPerMonth(message, conversationData);
            AnalyticsUtil.updateMessagesPerWeekday(message, conversationData);
            AnalyticsUtil.updateMessagesPerHour(message, conversationData);
        }

        return conversationData;
    }
}
