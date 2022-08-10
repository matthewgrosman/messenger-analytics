package analytics;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import shared.MongoDBClient;

public class AggregatedConversationMongoDB {
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
