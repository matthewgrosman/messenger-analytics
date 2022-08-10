package analytics;

import org.bson.Document;
import shared.Constants;

import java.util.Calendar;
import java.util.Date;

public class AnalyticsUtil {

    /**
     * Updates the messagesPerPerson HashMap contained within the SingleConversationData object with
     * the data from the current message. Adds message sender name as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerPerson(Document message, ConversationData conversationData) {
        String sender = message.get(Constants.MONGO_SENDER_FIELD_NAME).toString();
        conversationData.messagesPerPerson.put(sender, conversationData.messagesPerPerson.getOrDefault(sender, 0) + 1);
    }

    /**
     * Updates the messagesPerMonth HashMap contained within the SingleConversationData object with
     * the data from the current message. Adds the month message was sent as a key and updates the value
     * associated with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerMonth(Document message, ConversationData conversationData) {
        String date = getFormattedDate(message, Constants.MONTH_FORMAT);
        conversationData.messagesPerMonth.put(date, conversationData.messagesPerMonth.getOrDefault(date, 0) + 1);
    }

    /**
     * Updates the messagesPerWeekday HashMap contained within the SingleConversationData object with
     * the data from the current message. Adds the day message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerWeekday(Document message, ConversationData conversationData) {
        String weekday = getFormattedDate(message, Constants.WEEKDAY_FORMAT);
        conversationData.messagesPerWeekday.put(weekday, conversationData.messagesPerWeekday.getOrDefault(weekday, 0) + 1);
    }

    /**
     * Updates the messagesPerHour HashMap contained within the SingleConversationData object with the
     * data from the current message. Adds the hour message was sent as a key and updates the value associated
     * with the key by 1.
     *
     * @param message           A Document containing the current message and all of it's relevant data.
     * @param conversationData  A ConversationData object that holds all of the relevant data
     *                          for analytics.
     */
    public static void updateMessagesPerHour(Document message, ConversationData conversationData) {
        String hour = getFormattedDate(message, Constants.HOUR_FORMAT);
        conversationData.messagesPerHour.put(hour, conversationData.messagesPerHour.getOrDefault(hour, 0) + 1);
    }

    /**
     * Gets current number of messages as a percent of the total number of messages.
     *
     * @param totalNumberOfMessages     A long representing the total number of messages sent in the conversation.
     * @param currentNumberOfMessages   A long representing the current number of messages we want to find a
     *                                  percentage for.
     * @return                          A double representing the proportion of current messages to total
     *                                  messages as a percentage.
     */
    public static double getProportionAsPercentage(long totalNumberOfMessages, int currentNumberOfMessages) {
        // We need to convert one of these numbers to a decimal type in order to not
        // just get 0 when dividing the two numbers.
        return ((currentNumberOfMessages*1.0) / totalNumberOfMessages) * 100;
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
