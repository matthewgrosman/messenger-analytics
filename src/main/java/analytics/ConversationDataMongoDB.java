package analytics;

import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import shared.Constants;
import shared.MongoDBClient;

public class ConversationDataMongoDB {
    /**
     * Gets all relevant conversation data for the current conversation. This includes getting data on
     * conversation message counts, message senders, the months that messages were sent, the days of
     * the week that messages were sent, and the hours that messages were sent. This is all returned
     * neatly in an ConversationData object that has public class variables for all relevant
     * data collected in this function.
     *
     * @param conversationName  A String denoting the current conversation name if the user would like
     *                          to filter results to a single conversation level. This parameter is left
     *                          as null if the user would like to view aggregated data across all conversations.
     * @return                  An ConversationData object that contains all relevant
     *                          analytics data collected in this function.
     */
    public static ConversationData getConversationData(String conversationName) {
        if (conversationName != null && !isConversationValid(conversationName)) {
            return null; // In future let's throw an InvalidConversationName error.
        }

        ConversationData conversationData = new ConversationData();

        BasicDBObject query = new BasicDBObject();
        if (conversationName != null) {
            query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        }

        // Grab the total number of documents.
        conversationData.numberOfMessages = MongoDBClient.messagesCollection.countDocuments(query);

        // THIS WILL GET REFACTORED IN NEXT COMMIT!!! This is just here for now to get this first refactor
        // working- I don't want to make a huge CR with several refactors at once.
        if (conversationName == null) {
            // Get the number of messages per conversation. We do this by iterating through all
            // conversation names and record number of messages in each conversation.
            DistinctIterable<String> conversationNames = MongoDBClient.messagesCollection.distinct(
                    Constants.MONGO_CONVERSATION_FIELD_NAME, String.class);
            for (String conversation : conversationNames) {
                BasicDBObject conversationNameQuery = new BasicDBObject();
                conversationNameQuery.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversation);

                int numMessages = (int) MongoDBClient.messagesCollection.countDocuments(conversationNameQuery);
                conversationData.messagesPerConversation.put(conversation, numMessages);
            }
        }

        // Iterate through the messages and grab data we need from each message. This includes getting the
        // message sender, the month that message was sent, the day of week that the message was sent, and
        // the hour that the message was sent.
        FindIterable<Document> conversationMessages = MongoDBClient.messagesCollection.find(query);
        for (Document message : conversationMessages) {
            AnalyticsUtil.updateMessagesPerPerson(message, conversationData);
            AnalyticsUtil.updateMessagesPerMonth(message, conversationData);
            AnalyticsUtil.updateMessagesPerWeekday(message, conversationData);
            AnalyticsUtil.updateMessagesPerHour(message, conversationData);
        }

        return conversationData;
    }

    /**
     * Takes in a user entered conversation name and checks to see if that conversation exists in
     * the database.
     *
     * @param conversationName  A String representing the name of the conversation user wants analytics for.
     * @return                  A boolean denoting if the conversation is valid or not.
     */
    private static boolean isConversationValid(String conversationName) {
        // Check if at least one message has been sent in a conversation with this conversation name.
        BasicDBObject query = new BasicDBObject();
        query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        return MongoDBClient.messagesCollection.countDocuments(query) > 0;
    }
}
