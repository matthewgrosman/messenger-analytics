import analytics.AnalyticsUtil;
import analytics.ConversationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.bson.Document;
import shared.Constants;

public class AnalyticsUtilTest {
    @Test
    void testUpdateMessagesPerPersonWithNewPerson() {
        ConversationData conversationData = new ConversationData();
        Document validMessage = UnitTestConstants.VALID_MESSAGE;
        AnalyticsUtil.updateMessagesPerSender(validMessage, conversationData);

        String senderName = (String) validMessage.get(Constants.MONGO_SENDER_FIELD_NAME);
        int actualNumberOfMessages = conversationData.messagesPerSender.get(senderName);
        int expectedNumberOfMessages = 1;

        Assertions.assertEquals(expectedNumberOfMessages, actualNumberOfMessages);
    }

    @Test
    void testUpdateMessagesPerPersonWithExistingPerson() {
    }

    @Test
    void testUpdateMessagesPerConversationWithNewConversation() {
    }

    @Test
    void testUpdateMessagesPerConversationWithExistingConversation() {
    }

    @Test
    void testUpdateMessagesPerMonthWithNewMonth() {
    }

    @Test
    void testUpdateMessagesPerMonthWithExistingMonth() {
    }

    @Test
    void testUpdateMessagesPerWeekdayWithNewWeekday() {
    }

    @Test
    void testUpdateMessagesPerWeekdayWithExistingWeekday() {
    }

    @Test
    void testUpdateMessagesPerHourWithNewHour() {
    }

    @Test
    void testUpdateMessagesPerHourWithExistingHour() {
    }
}
