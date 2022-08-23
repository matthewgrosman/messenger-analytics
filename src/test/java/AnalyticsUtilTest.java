import analytics.AnalyticsUtil;
import analytics.ConversationData;
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
    void testUpdateMessagesPerMonthWithNewMonth() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerMonth(validMessage, conversationData);

        String month = AnalyticsUtil.getFormattedDate(validMessage, Constants.MONTH_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerMonth.get(month);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerMonthWithExistingMonth() {
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
    void testUpdateMessagesPerWeekdayWithNewWeekday() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerWeekday(validMessage, conversationData);

        String weekday = AnalyticsUtil.getFormattedDate(validMessage, Constants.WEEKDAY_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerWeekday.get(weekday);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerWeekdayWithExistingWeekday() {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sent month, to simulate adding two
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
    void testUpdateMessagesPerHourWithNewHour() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE_1;
        AnalyticsUtil.updateMessagesPerHour(validMessage, conversationData);

        String hour = AnalyticsUtil.getFormattedDate(validMessage, Constants.HOUR_FORMAT);
        int actualNumberOfMessages = conversationData.messagesPerHour.get(hour);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerHourWithExistingHour() {
        ConversationData conversationData = new ConversationData();

        // Both of these messages have the same sent month, to simulate adding two
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
    void testGetFormattedDateForMonth() {
    }

    @Test
    void testGetFormattedDateForWeekday() {
    }

    @Test
    void testGetFormattedDateForHour() {
    }

    @Test
    void testGetFormattedDateForInvalidTimePeriod() {
    }
}
