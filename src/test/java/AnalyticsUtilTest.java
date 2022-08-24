import analytics.AnalyticsUtil;
import analytics.ConversationData;
import analytics.InvalidDateFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.bson.Document;
import shared.Constants;

public class AnalyticsUtilTest {
    @Test
    void testUpdateMessagesPerSenderWithNewSender() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerSender(validMessage, conversationData);

        String senderName = (String) validMessage.get(Constants.MONGO_SENDER_FIELD_NAME);
        int actualNumberOfMessages = conversationData.messagesPerSender.get(senderName);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerSenderWithExistingSender() {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sender name, to simulate adding two
        // messages from the same sender to the HashMap.
        Document validMessage1 = UnitTestConstants.VALID_MESSAGE_1;
        Document validMessage2 = UnitTestConstants.VALID_MESSAGE_2;
        AnalyticsUtil.updateMessagesPerSender(validMessage1, conversationData);
        AnalyticsUtil.updateMessagesPerSender(validMessage2, conversationData);

        String senderName = (String) validMessage1.get(Constants.MONGO_SENDER_FIELD_NAME);
        int actualNumberOfMessages = conversationData.messagesPerSender.get(senderName);
        int expectedNumberOfMessages = 2;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerSenderWithUniqueSenders() {

    }

    @Test
    void testUpdateMessagesPerConversationWithNewConversation() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerConversation(validMessage, conversationData);

        String conversationName = (String) validMessage.get(Constants.MONGO_CONVERSATION_FIELD_NAME);
        int actualNumberOfMessages = conversationData.messagesPerConversation.get(conversationName);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerConversationWithExistingConversation() {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same conversation name, to simulate adding two
        // messages from the same conversation to the HashMap.
        Document validMessage1 = UnitTestConstants.VALID_MESSAGE_1;
        Document validMessage2 = UnitTestConstants.VALID_MESSAGE_2;
        AnalyticsUtil.updateMessagesPerConversation(validMessage1, conversationData);
        AnalyticsUtil.updateMessagesPerConversation(validMessage2, conversationData);

        String conversationName = (String) validMessage1.get(Constants.MONGO_CONVERSATION_FIELD_NAME);
        int actualNumberOfMessages = conversationData.messagesPerConversation.get(conversationName);
        int expectedNumberOfMessages = 2;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerConversationWithUniqueConversations() {

    }

    @Test
    void testUpdateMessagesPerMonthWithNewMonth() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerMonth(validMessage, conversationData);

        String month = AnalyticsUtil.getFormattedDate(validMessage, Constants.MONTH_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerMonth.get(month);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerMonthWithExistingMonth() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sent month, to simulate adding two
        // messages from the same sent month to the HashMap.
        Document validMessage1 = UnitTestConstants.VALID_MESSAGE_1;
        Document validMessage2 = UnitTestConstants.VALID_MESSAGE_2;
        AnalyticsUtil.updateMessagesPerMonth(validMessage1, conversationData);
        AnalyticsUtil.updateMessagesPerMonth(validMessage2, conversationData);

        String month = AnalyticsUtil.getFormattedDate(validMessage1, Constants.MONTH_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerMonth.get(month);
        int expectedNumberOfMessages = 2;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerMonthWithUniqueMonths() {

    }

    @Test
    void testUpdateMessagesPerWeekdayWithNewWeekday() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerWeekday(validMessage, conversationData);

        String weekday = AnalyticsUtil.getFormattedDate(validMessage, Constants.WEEKDAY_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerWeekday.get(weekday);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerWeekdayWithExistingWeekday() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sent weekday, to simulate adding two
        // messages from the same sent month to the HashMap.
        Document validMessage1 = UnitTestConstants.VALID_MESSAGE_1;
        Document validMessage2 = UnitTestConstants.VALID_MESSAGE_2;
        AnalyticsUtil.updateMessagesPerWeekday(validMessage1, conversationData);
        AnalyticsUtil.updateMessagesPerWeekday(validMessage2, conversationData);

        String weekday = AnalyticsUtil.getFormattedDate(validMessage1, Constants.WEEKDAY_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerWeekday.get(weekday);
        int expectedNumberOfMessages = 2;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerWeekdayWithUniqueWeekdays() {

    }

    @Test
    void testUpdateMessagesPerHourWithNewHour() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerHour(validMessage, conversationData);

        String hour = AnalyticsUtil.getFormattedDate(validMessage, Constants.HOUR_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerHour.get(hour);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerHourWithExistingHour() throws InvalidDateFormatException {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sent hour, to simulate adding two
        // messages from the same sent month to the HashMap.
        Document validMessage1 = UnitTestConstants.VALID_MESSAGE_1;
        Document validMessage2 = UnitTestConstants.VALID_MESSAGE_2;
        AnalyticsUtil.updateMessagesPerHour(validMessage1, conversationData);
        AnalyticsUtil.updateMessagesPerHour(validMessage2, conversationData);

        String hour = AnalyticsUtil.getFormattedDate(validMessage1, Constants.HOUR_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerHour.get(hour);
        int expectedNumberOfMessages = 2;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerHourWithUniqueHours() {

    }

    @Test
    void testGetFormattedDateForMonth() throws InvalidDateFormatException {
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;

        String expectedMonth = "10-2019";
        String actualMonth = AnalyticsUtil.getFormattedDate(validMessage, Constants.MONTH_FORMAT);

        Assertions.assertEquals(expectedMonth, actualMonth);
    }

    @Test
    void testGetFormattedDateForWeekday() throws InvalidDateFormatException {
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;

        String expectedWeekday = "Saturday";
        String actualWeekday = AnalyticsUtil.getFormattedDate(validMessage, Constants.WEEKDAY_FORMAT);

        Assertions.assertEquals(expectedWeekday, actualWeekday);
    }

    @Test
    void testGetFormattedDateForHour() throws InvalidDateFormatException {
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;

        String expectedHour = "12:00";
        String actualHour = AnalyticsUtil.getFormattedDate(validMessage, Constants.HOUR_FORMAT);

        Assertions.assertEquals(expectedHour, actualHour);
    }

    @Test
    void testGetFormattedDateForInvalidTimePeriod() throws InvalidDateFormatException {
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;

        Exception exception = Assertions.assertThrows(InvalidDateFormatException.class, () ->
                AnalyticsUtil.getFormattedDate(validMessage, "SOME_INVALID_TIME"));

        System.out.println(exception.getMessage());

        Assertions.assertEquals(exception.getMessage(), Constants.INVALID_DATE_EXCEPTION_MESSAGE);
    }
}
