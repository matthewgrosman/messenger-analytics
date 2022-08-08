package analytics;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import shared.Constants;
import shared.MongoDBClient;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class IndividualConversationMongoDB {
    /**
     * Takes in a user entered conversation name and checks to see if that conversation exists in
     * the database.
     *
     * @param conversationName  A String representing the name of the conversation user wants analytics for.
     * @return                  A boolean denoting if the conversation is valid or not.
     */
    public static boolean isConversationValid(String conversationName) {
        // Check if at least one message has been sent in a conversation with this conversation name.
        BasicDBObject query = new BasicDBObject();
        query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        return MongoDBClient.messagesCollection.countDocuments(query) > 0;
    }

    /**
     * Takes in a conversation name and returns the amount of messages sent in that conversation.
     *
     * @param conversationName  A String representing the name of the conversation.
     * @return                  A long representing the number of messages in the conversation.
     */
    public static long getNumberOfMessages(String conversationName) {
        BasicDBObject query = new BasicDBObject();
        query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        return MongoDBClient.messagesCollection.countDocuments(query);
    }

    /**
     * Takes in a conversation name and returns a HashMap that contains all of the conversation
     * participants and how many messages they have sent.
     *
     * @param conversationName  A String representing the name of the conversation.
     * @return                  A HashMap containing the names of all participants and the number
     *                          of messages that they have sent in the conversation.
     */
    public static HashMap<String, Integer> getNumberOfMessagesPerPerson(String conversationName) {
        HashMap<String, Integer> messagesPerPerson = new HashMap<>();
        FindIterable<Document> messages = getMessagesFromConversation(conversationName);

        for (Document message : messages) {
            String sender = message.get(Constants.MONGO_SENDER_FIELD_NAME).toString();
            messagesPerPerson.put(sender, messagesPerPerson.getOrDefault(sender, 0) + 1);
        }

        return messagesPerPerson;
    }

    /**
     * Takes in a conversation name and returns a HashMap that contains all of the months messages
     * have been sent and how many messages have been sent each month.
     *
     * @param conversationName  A String representing the name of the conversation.
     * @return                  A HashMap containing the months messages have been sent and
     *                          the amount of messages that sent in each month.
     */
    public static HashMap<String, Integer> getNumberOfMessagesPerMonth(String conversationName) {
        HashMap<String, Integer> messagesPerMonth = new HashMap<>();
        FindIterable<Document> messages = getMessagesFromConversation(conversationName);

        for (Document message : messages) {
            String date = getFormattedDate(message, Constants.MONTH_FORMAT);
            messagesPerMonth.put(date, messagesPerMonth.getOrDefault(date, 0) + 1);
        }

        return messagesPerMonth;
    }

    /**
     * Takes in a conversation name and returns a HashMap that contains all of the weekdays messages
     * have been sent and how many messages have been sent each weekday.
     *
     * @param conversationName  A String representing the name of the conversation.
     * @return                  A HashMap containing the weekdays messages have been sent and
     *                          the amount of messages that sent on each weekday..
     */
    public static HashMap<String, Integer> getNumberOfMessagesPerWeekday(String conversationName) {
        HashMap<String, Integer> messagesPerWeekday = new HashMap<>();
        FindIterable<Document> messages = getMessagesFromConversation(conversationName);

        for (Document message : messages) {
            String weekday = getFormattedDate(message, Constants.WEEKDAY_FORMAT);
            messagesPerWeekday.put(weekday, messagesPerWeekday.getOrDefault(weekday, 0) + 1);
        }

        return messagesPerWeekday;
    }

    /**
     * Filter down database documents to a specific conversation, and return an iterable object
     * that can be iterated on to access those documents.
     *
     * @param conversationName  A String representing the name of the conversation.
     * @return                  A FindIterable<Document> object that is an iterable of all
     *                          documents for the given conversation.
     */
    private static FindIterable<Document> getMessagesFromConversation(String conversationName) {
        BasicDBObject query = new BasicDBObject();
        query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);
        return MongoDBClient.messagesCollection.find(query);
    }

    /**
     * Takes in a document that has a date field and returns a formatted date. The format is
     * indicated by the formatType parameter.
     *
     * @param document      A message Document that contains a date field.
     * @param formatType    A String that denotes what format we want the date in.
     * @return              A String with the correctly formatted date.
     */
    private static String getFormattedDate(Document document, String formatType) {
        Date date = (Date) document.get(Constants.MONGO_DATE_FIELD_NAME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (formatType.equals(Constants.MONTH_FORMAT)) {
            // This line gets month number, but it starts at a 0th index (i.e. January is the
            // 0th month), so we add 1 to get the normal month number.
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            return month + "-" + year;
        }
        else if (formatType.equals(Constants.WEEKDAY_FORMAT)) {
            int weekdayIndex = calendar.get(Calendar.DAY_OF_WEEK);
            return Constants.WEEKDAY_INDEX_TO_DAY.get(weekdayIndex);
        }
        else {
            return "";
        }
    }
}
