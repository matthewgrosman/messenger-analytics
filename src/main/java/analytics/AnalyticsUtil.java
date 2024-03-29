package analytics;

import exceptions.InvalidDateFormatException;
import org.bson.Document;
import shared.Constants;

import java.util.Calendar;
import java.util.Date;

public class AnalyticsUtil {

    /**
     * Updates the messagesPerSender HashMap contained within the ConversationData object with
     * the data from the current message. Adds message sender name as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerSender(Document message, ConversationData conversationData) {
        String sender = message.get(Constants.MONGO_SENDER_FIELD_NAME).toString();
        conversationData.messagesPerSender.put(sender, conversationData.messagesPerSender.getOrDefault(sender, 0) + 1);
    }

    /**
     * Updates the messagesPerConversation HashMap contained within the ConversationData object with the
     * data from the current message. Adds the conversation name as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerConversation(Document message, ConversationData conversationData) {
        String conversationName = message.get(Constants.MONGO_CONVERSATION_FIELD_NAME).toString();
        conversationData.messagesPerConversation.put(conversationName, conversationData.messagesPerConversation.getOrDefault(
                conversationName, 0) + 1);
    }

    /**
     * Updates the messagesPerMonth HashMap contained within the ConversationData object with
     * the data from the current message. Adds the month message was sent as a key and updates the value
     * associated with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     * @throws InvalidDateFormatException       ConversationDataMongoDB.getConversationData() can throw an
     *                                          InvalidDateFormatException.
     */
    public static void updateMessagesPerMonth(Document message, ConversationData conversationData) throws InvalidDateFormatException {
        String date = getFormattedDate(message, Constants.MONTH_FORMAT);
        conversationData.messagesPerMonth.put(date, conversationData.messagesPerMonth.getOrDefault(date, 0) + 1);
    }

    /**
     * Updates the messagesPerWeekday HashMap contained within the ConversationData object with
     * the data from the current message. Adds the day message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     * @throws InvalidDateFormatException       ConversationDataMongoDB.getConversationData() can throw an
     *                                          InvalidDateFormatException.
     */
    public static void updateMessagesPerWeekday(Document message, ConversationData conversationData) throws InvalidDateFormatException {
        String weekday = getFormattedDate(message, Constants.WEEKDAY_FORMAT);
        conversationData.messagesPerWeekday.put(weekday, conversationData.messagesPerWeekday.getOrDefault(weekday, 0) + 1);
    }

    /**
     * Updates the messagesPerHour HashMap contained within the ConversationData object with the
     * data from the current message. Adds the hour message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     * @throws InvalidDateFormatException       ConversationDataMongoDB.getConversationData() can throw an
     *                                          InvalidDateFormatException.
     */
    public static void updateMessagesPerHour(Document message, ConversationData conversationData) throws InvalidDateFormatException {
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
     * @throws InvalidDateFormatException       ConversationDataMongoDB.getConversationData() can throw an
     *                                          InvalidDateFormatException.
     */
    public static String getFormattedDate(Document document, String formatType) throws InvalidDateFormatException {
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

        throw new InvalidDateFormatException(Constants.INVALID_DATE_EXCEPTION_MESSAGE);
    }
}
