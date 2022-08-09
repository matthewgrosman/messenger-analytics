package analytics;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import shared.Constants;
import shared.MongoDBClient;

import java.util.Calendar;
import java.util.Date;

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
     * Gets all relevant conversation data for the current conversation. This includes getting the
     * message sender, the month that message was sent, the day of week that the message was sent, and
     * the hour that the message was sent. This is all returned neatly in an IndividualConversationData
     * object that has public class variables for all relevant data collected in this function.
     *
     * @param conversationName  A string denoting the current conversation name.
     * @return                  An IndividualConversationData object that contains all relevant
     *                          analytics data collected in this function.
     */
    public static IndividualConversationData getConversationData(String conversationName) {
        IndividualConversationData conversationData = new IndividualConversationData();

        // Create a query that filters messages to only be from the current conversation
        BasicDBObject query = new BasicDBObject();
        query.put(Constants.MONGO_CONVERSATION_FIELD_NAME, conversationName);

        // Grab the total number of documents
        conversationData.numberOfMessages = MongoDBClient.messagesCollection.countDocuments(query);

        // Iterate through the messages and grab data we need from each message. This includes getting the
        // message sender, the month that message was sent, the day of week that the message was sent, and
        // the hour that the message was sent.
        FindIterable<Document> conversationMessages = MongoDBClient.messagesCollection.find(query);
        for (Document message : conversationMessages) {
            updateMessagesPerPerson(message, conversationData);
            updateMessagesPerMonth(message, conversationData);
            updateMessagesPerWeekday(message, conversationData);
            updateMessagesPerHour(message, conversationData);
        }

        return conversationData;
    }

    /**
     * Updates the messagesPerPerson HashMap contained within the IndividualConversationData object with
     * the data from the current message. Adds message sender name as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  An IndividualConversationData object that holds all of the aggregated data
     *                          for analytics.
     */
    public static void updateMessagesPerPerson(Document message, IndividualConversationData conversationData) {
        String sender = message.get(Constants.MONGO_SENDER_FIELD_NAME).toString();
        conversationData.messagesPerPerson.put(sender, conversationData.messagesPerPerson.getOrDefault(sender, 0) + 1);
    }

    /**
     * Updates the messagesPerMonth HashMap contained within the IndividualConversationData object with
     * the data from the current message. Adds the month message was sent as a key and updates the value
     * associated with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  An IndividualConversationData object that holds all of the aggregated data
     *                          for analytics.
     */
    public static void updateMessagesPerMonth(Document message, IndividualConversationData conversationData) {
        String date = getFormattedDate(message, Constants.MONTH_FORMAT);
        conversationData.messagesPerMonth.put(date, conversationData.messagesPerMonth.getOrDefault(date, 0) + 1);
    }

    /**
     * Updates the messagesPerWeekday HashMap contained within the IndividualConversationData object with
     * the data from the current message. Adds the day message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  An IndividualConversationData object that holds all of the aggregated data
     *                          for analytics.
     */
    public static void updateMessagesPerWeekday(Document message, IndividualConversationData conversationData) {
        String weekday = getFormattedDate(message, Constants.WEEKDAY_FORMAT);
        conversationData.messagesPerWeekday.put(weekday, conversationData.messagesPerWeekday.getOrDefault(weekday, 0) + 1);
    }

    /**
     * Updates the messagesPerHour HashMap contained within the IndividualConversationData object with the
     * data from the current message. Adds the hour message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  An IndividualConversationData object that holds all of the aggregated data
     *                          for analytics.
     */
    public static void updateMessagesPerHour(Document message, IndividualConversationData conversationData) {
        String hour = getFormattedDate(message, Constants.HOUR_FORMAT);
        conversationData.messagesPerHour.put(hour, conversationData.messagesPerHour.getOrDefault(hour, 0) + 1);
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
        else if (formatType.equals(Constants.HOUR_FORMAT)){
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            return hour + Constants.HOUR_SUFFIX;
        }

        return "";
    }
}
